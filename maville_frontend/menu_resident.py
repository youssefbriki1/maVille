import streamlit as st
import logging 
import requests
from menu import Menu
from personne import Personne
from util_text import API_URL, TYPES_TRAVAIL
from formParser import DataParser
import json
import ast

logger = logging.getLogger(__name__)

def get_horraires_by_email_and_role(email, role):
    try:
        with open('../maville_backend/data/users.json', 'r') as file:
            users = json.load(file)
            for user in users:
                if user.get('email') == email and user.get('role') == role:
                    return user.get('horraires', {})
        return None
    except FileNotFoundError:
        print("The file users.json was not found.")
        return None
    except json.JSONDecodeError:
        print("Error decoding JSON from the file.")
        return None
def format_horraires(horraires):
    if not horraires or not horraires.get('days') or not horraires.get('times'):
        return "Vous n'avez pas défini de préférences."

    days = horraires['days']
    times = horraires['times']
    start_time = times['start_time']
    end_time = times['end_time']

    # Convert times to a more readable format
    start_time_formatted = start_time[:-3]  # Remove seconds
    end_time_formatted = end_time[:-3]  # Remove seconds

    if len(days) == 1:
        days_str = days[0]
    else:
        days_str = ' et '.join([', '.join(days[:-1]), days[-1]]) if len(days) > 1 else days[0]
    if len(days) == 7:
        return f"Vous êtes ok tous les jours de la semaine de {start_time_formatted} à {end_time_formatted}."
    return f"Vous êtes ok le {days_str} de {start_time_formatted} à {end_time_formatted}."

class Menu_Resident(Menu):
    def __init__(self,user:Personne) -> None:
        super().__init__()
        self.notification_title = "Voir Notifications" if st.session_state.get("Notification_number") == 0 else f"Vous avez {st.session_state.get('Notification_number')} nouvelles notifications"
        self.options = ["Acceuil","Consulter Entraves", "Consulter Travaux", "Consulter Travaux à venir","Consulter travaux par quartier","Consulter Entraves par Rue","Consulter travaux residentiels", "Soumettre requete travail","Consulter Profile","Définir ses horraires", self.notification_title, "Se deconnecter"]    
        self.user = user
        
        
    def __fetch_api_data(self, user_choice):
        """
        Fetch data from the API and writes it.

        Args:
            user_choice (_type_): _description_
        """
        curr_data = {"choix": user_choice}
        response = requests.get(f"{API_URL}/fetch-data", params=curr_data)
        if response.status_code != 200:
            st.error("Failed to retrieve data.")
            self.logger.error(f"Failed to retrieve data: {response.text}")
            return
        else:
            data_parser = DataParser()
            response_data = data_parser.formParser(response.json())
            data_parser.write(response_data)
        

    def consulter_profile(self):
        st.title("Profile")
        st.write("Email: "+self.user.email) 
        st.write("Role: "+self.user.role)
        
    def consulter_entraves(self):
        st.title("Entraves")
        self.__fetch_api_data("entraves")
    
    def consulter_travaux(self):
        st.title("Travaux")
        self.__fetch_api_data("travaux")    
        
    def consulter_travaux_residentiels(self):
        response = requests.get(f"{API_URL}/consulter_infos", params=self.user.to_dict())
        
        if response.status_code == 200:
            requests_data = response.json()
            for request in requests_data:
                st.write(f" - Faite par ({request['senderEmail']})")
                st.write(f"Titre: {request['title']}")
                st.write(f"Description: {request['description']}")
                st.write(f"Type de travail: {request['typeTravaux']}")
                st.write(f"Date de debut: {request['dateDebut']}")
                st.write(f"Status: {request['status']}")
                
                if request['status'].startswith("Candidature soumise par"):
                    intervenant_email = request['status'].split("par ")[1]
                    if st.button(f"Accepter {request['title']}"):
                        self.update_status(request['id'], "Accepté par résident et intervenant")
                    if st.button(f"Refuser {request['title']}"):
                        self.update_status(request['id'], "En attente")
                        self.remove_requete_from_intervenant(intervenant_email, request['id'])
                st.write('---')
        else:
            st.error("Failed to retrieve requests.")

    def update_status(self, request_id, new_status):
        data = {
            "request_id": request_id,
            "new_status": new_status
        }
        response = requests.post(f"{API_URL}/update_status", json=data)
        if response.status_code == 200:
            st.success("Status updated successfully")
        else:
            st.error("Failed to update status")
            st.error(response.text)

    def remove_requete_from_intervenant(self, intervenant_email, request_id):
        data = {
            "email": intervenant_email,
            "travail_id": request_id
        }
        response = requests.post(f"{API_URL}/get_candidatures/remove", json=data)
        if response.status_code == 200:
            st.success("Candidature retirée avec succès")
            
        else:
            st.error("Failed to remove candidature")
            st.error(response.text)
                
    def soumettre_requete_travail(self):
        with st.form(key='request_form'):
            st.write('Enter your request below:')
            title = st.text_input('Titre')
            description = st.text_area('Description')
            type_travail = st.selectbox('Type de travail', TYPES_TRAVAIL)
            date_debut = st.date_input('Date de debut')
            submit_button = st.form_submit_button(label='Submit Request')

        if submit_button:
            if not title or not description:
                st.warning("Please enter both title and description.")
            else:
                data = {
                    "resident_email": st.session_state.get("user_email"),
                    "type_travail": type_travail,
                    "titre": title,
                    "description": description,
                    "date_debut": str(date_debut)
                }
                try:
                    with st.spinner('Sending request...'):
                        response = requests.post(f"{API_URL}/envoyer_requete_resident", json=data)
                    if response.status_code == 200:
                        st.success('Request submitted successfully!')
                    else:
                        st.error(f"Failed to submit request: {response.text}")
                except requests.exceptions.RequestException as e:
                    st.error(f"An error occurred: {e}")
                    
                    
    def voir_notifications(self):
        response = requests.get(f"{API_URL}/consulter_notifications", params=self.user.to_dict())
        if response.status_code == 200:
            notifications = response.json()
            
            
            new_notifications = [notif for notif in notifications['new']]
            old_notifications = [notif for notif in notifications['old']]
            
            st.write("### Nouvelles Notifications")
            if new_notifications:
                for notification in new_notifications:
                    st.write(f"**Titre:** {notification['title']}")
                    st.write(f"**Description:** {notification['description']}")
                    st.write(f"**Date:** {notification['date']}")
                    st.write("---")
            else:
                st.write("Pas de nouvelles notifications.")
            
            st.write("### Anciennes Notifications")
            if old_notifications:
                for notification in old_notifications:
                    st.write(f"**Titre:** {notification['title']}")
                    st.write(f"**Description:** {notification['description']}")
                    st.write(f"**Date:** {notification['date']}")
                    st.write("---")
            else:
                st.write("Pas d'anciennes notifications.")
        else:
            st.error(f"Failed to retrieve notifications: {response.text}")
            
            
    
    def definir_horraires(self):
        horraires = get_horraires_by_email_and_role(self.user.email, self.user.role)
        st.write('Vos horraires actuels : ' + format_horraires(horraires))
        with st.form(key='horraire_form'):
            st.write("Définissez vos préférences d'horraire :")
            days_of_week = st.multiselect(
                'Selection des jours',
                ['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche']
            )
            start_time = st.time_input('Heure de début')
            end_time = st.time_input('Heure de fin')
            submit_button = st.form_submit_button(label="Soumettre vos préférences d'horraire")

        if submit_button:
            if not days_of_week or not start_time or not end_time:
                st.warning("Veuillez tout remplir.")
            else:
                data = {
                    "resident_email": st.session_state.get("user_email"),
                    "days_of_week": days_of_week,
                    "start_time": str(start_time),
                    "end_time": str(end_time),
                    "resident" : self.user.to_dict()
                }
                try:
                    with st.spinner('Sending horraire... '):
                        response = requests.post(f"{API_URL}/definir-horraires", json=data)
                    if response.status_code == 200:
                        st.success('Horraire submitted successfully! ' + response.text)
                        st.session_state['horraires'] = format_horraires(ast.literal_eval(response.text))
                        st.rerun()
                    else:
                        st.error(f"Failed to submit horraire: {response.text}")
                except requests.exceptions.RequestException as e:
                    st.error(f"An error occurred: {e}")

    def __fetch_and_filter_quartier_api_data(self, user_choice, filter_key, filter_value):
        """
        Fetch data from the API, filter it, and write the filtered results.

        Args:
            user_choice (str): The type of data to fetch (e.g., 'travaux').
            filter_key (str): The key to filter by (e.g., 'boroughid').
            filter_value (str): The value to match for the filter (e.g., 'Le Sud-Ouest').
        """
        curr_data = {"choix": user_choice}
        response = requests.get(f"{API_URL}/fetch-data", params=curr_data)
        if response.status_code != 200:
            st.error("Failed to retrieve data.")
            self.logger.error(f"Failed to retrieve data: {response.text}")
            return
        else:
            try:
                # Récupérer les données brutes
                raw_data = response.json()

                # Filtrer les données par le critère spécifié
                filtered_data = [item for item in raw_data if item.get(filter_key, "").lower() == filter_value.lower()]
            
                if filtered_data:
                    # Parser et afficher les données filtrées
                    data_parser = DataParser()
                    response_data = data_parser.formParser(filtered_data)
                    data_parser.write(response_data)
                else:
                    st.info(f"Aucun élément trouvé pour le filtre {filter_key} = {filter_value}.")
            except Exception as e:
                st.error(f"Erreur lors du traitement des données : {e}")



    def __fetch_and_filter_rue_api_data(self, user_choice, filter_key, filter_value):
        """
        Fetch data from the API, filter it, and write the filtered results.

        Args:
            user_choice (str): The type of data to fetch (e.g., 'travaux').
            filter_key (str): The key to filter by (e.g., 'streetid').
            filter_value (str): The value to match for the filter (e.g., 'rue Ontario Est').
        """
        curr_data = {"choix": user_choice}
        response = requests.get(f"{API_URL}/fetch-data", params=curr_data)
        if response.status_code != 200:
            st.error("Failed to retrieve data.")
            self.logger.error(f"Failed to retrieve data: {response.text}")
            return
        else:
            try:
                # Récupérer les données brutes
                raw_data = response.json()
            
                # Filtrer les données par le critère spécifié
                filtered_data = [
                    item for item in raw_data
                    if filter_key in item and item[filter_key].strip().lower() == filter_value.strip().lower()
                ]
            
                if filtered_data:
                    # Parser et afficher les données filtrées
                    data_parser = DataParser()
                    response_data = data_parser.formParser(filtered_data)
                    data_parser.write(response_data)
                else:
                    st.info(f"Aucun élément trouvé pour le filtre {filter_key} = {filter_value}.")
            except Exception as e:
                st.error(f"Erreur lors du traitement des données : {e}")
    def consulter_travaux_par_quartier(self):
        st.title("Travaux par Quartier")

        # Entrée utilisateur pour le quartier
        quartier = st.text_input("Entrez le nom du quartier :", "")

        if quartier:
            # Récupérer et filtrer les données par quartier
            self.__fetch_and_filter_quartier_api_data("travaux", "boroughid", quartier)

    def consulter_entraves_par_rue(self):
        st.title("Entraves par Rue")

        # Entrée utilisateur pour la rue
        rue = st.text_input("Entrez le nom de la rue :", "")

        if rue:
            # Récupérer et filtrer les données par rue
            self.__fetch_and_filter_rue_api_data("entraves", "streetid", rue)                


    def consulter_futur_travaux(self):
        st.title("Travaux à venir")
        try:
            response = requests.get(f"{API_URL}/futur_travaux", params=self.user.to_dict())
            if response.status_code == 200:
                travaux_a_venir = response.json()
                if travaux_a_venir:
                    for travail in travaux_a_venir:
                        st.write(f"Titre: {travail['title']}")
                        st.write(f"Description: {travail['description']}")
                        st.write(f"Type de travail: {travail['typeTravaux']}")
                        st.write(f"Date de début: {travail['dateDebut']}")
                        st.write(f"Status: {travail['status']}")
                        st.write('---')
                else:
                    st.info("Aucun travail prévu dans les 3 prochains mois.")
            else:
                st.error(f"Erreur : {response.status_code} - {response.text}")
        except Exception as e:
            st.error(f"Une erreur est survenue : {str(e)}")

    def __call__(self):
        self.sidebar()
        if self.selection == "Acceuil":
            self.page_acceuil()
        elif self.selection == "Consulter Entraves":
            self.consulter_entraves()
        elif self.selection == "Consulter Travaux":
            self.consulter_travaux()
        elif self.selection == "Consulter Travaux à venir":
            self.consulter_futur_travaux()
        elif self.selection == "Consulter travaux par quartier":
            self.consulter_travaux_par_quartier()
        elif self.selection == "Consulter Entraves par Rue":
            self.consulter_entraves_par_rue()
        elif self.selection == "Consulter travaux residentiels":
            self.consulter_travaux_residentiels()
        elif self.selection == "Soumettre requete travail":
            self.soumettre_requete_travail()
        elif self.selection == "Consulter Profile":
            self.consulter_profile()
        elif self.selection == "Définir ses horraires":
            self.definir_horraires()   
        elif self.selection == self.notification_title: 
            self.voir_notifications()
        elif self.selection == "Se deconnecter":
            self.se_deconnecter()
