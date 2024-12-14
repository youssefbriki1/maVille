import streamlit as st
import logging 
import requests
from menu import Menu
from personne import Personne
from util_text import API_URL, TYPES_TRAVAIL
from formParser import DataParser
import json
import ast

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

    return f"Vous êtes ok le {days_str} de {start_time_formatted} à {end_time_formatted}."

class Menu_Resident(Menu):
    def __init__(self,user:Personne) -> None:
        super().__init__()
        self.options = ["Acceuil","Consulter Entraves", "Consulter Travaux", "Consulter travaux residentiels", "Soumettre requete travail","Consulter Profile","Définir ses horraires", "Se deconnecter"]    
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
            # To parse here - add parser class:
            for request in requests_data:
                st.write(f" - Faite part ({request['senderEmail']})")
                st.write(f"Titre: {request['title']}")
                st.write(f"Description: {request['description']}")
                st.write(f"Type de travail: {request['typeTravaux']}")
                st.write(f"Date de debut: {request['dateDebut']}")
                st.write(f"Status: {request['status']}")
                st.write('---')
        else:
            st.error("Failed to retrieve requests.")
            
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
        response = requests.get(f"{API_URL}/consulter-notifications", params=self.user.to_dict())
        if response.status_code == 200:
            notifications = response.json()
            for notification in notifications:
                st.write(f"Notification: {notification['message']}")
        else:
            st.error("Failed to retrieve notifications.")

        
            
    
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
                print(days_of_week)
                print(self.user.to_dict())
                data = {
                    "resident_email": st.session_state.get("user_email"),
                    "days_of_week": days_of_week,
                    "start_time": str(start_time),
                    "end_time": str(end_time),
                    "resident" : self.user.to_dict()
                }
                print(data)
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
    def __call__(self):
        self.sidebar()
        if self.selection == "Acceuil":
            self.page_acceuil()
        elif self.selection == "Consulter Entraves":
            self.consulter_entraves()
        elif self.selection == "Consulter Travaux":
            self.consulter_travaux()
        elif self.selection == "Consulter travaux residentiels":
            self.consulter_travaux_residentiels()
        elif self.selection == "Soumettre requete travail":
            self.soumettre_requete_travail()
        elif self.selection == "Consulter Profile":
            self.consulter_profile()
        elif self.selection == "Définir ses horraires":
            self.definir_horraires()
        elif self.selection == "Se deconnecter":
            self.se_deconnecter()
