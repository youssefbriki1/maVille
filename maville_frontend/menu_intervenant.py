from menu import Menu
import streamlit as st
import requests
import logging
from util_text import API_URL,TYPES_TRAVAIL
from personne import Personne
from streamlit_js_eval import streamlit_js_eval
from datetime import datetime
import re

def extract_email(string):
    email_pattern = r'by ([\w\.-]+@[\w\.-]+)'
    match = re.search(email_pattern, string)
    if match:
        return match.group(1)
    return None

class Menu_Intervenant(Menu):
    def __init__(self,user:Personne) -> None:
        super().__init__()
        self.options = ["Acceuil","Consulter Travaux","Soumettre candidature", "Consulter Profile", "Soumettre projet","Modifier status projet","Se deconnecter"]    
        self.user = user
        
    def consulter_travaux(self):
        response = requests.get(f"{API_URL}/consulter_infos", params=self.user.to_dict())
        if response.status_code == 200:
            requests_data = response.json()
            st.title("Travaux")
            st.write("Voici les travaux en cours:")
            st.write("Vous ne pouvez pas soumettre de requete pour ces travaux.")
            st.write("-------------------------------------------------")
            # To parse here - add parser class:
            for request in requests_data:
                st.write(f" - Faite part ({request['senderEmail']})")
                st.write(f"Titre: {request['title']}")
                st.write(f"Description: {request['description']}")
                st.write(f"Type de travail: {request['typeTravaux']}")
                st.write(f"Date de debut: {request['dateDebut']}")
                st.write(f"Status: {request['status']}")
                st.write('---')


    def consulter_profile(self):
        st.title("Profile")
        st.write("Email: "+self.user.email) 
        st.write("Role: "+self.user.role)
        
        
    def soumettre_requete_travail(self):
        with st.form(key='request_form'):
            st.write('Enter your request below:')
            id = st.text_input('Id de la requete')
            description = st.text_area('Description')
            date_debut = st.date_input('Date de debut')
            submit_button = st.form_submit_button(label='Submit Request')


        ############################## - To edit
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
        ##############################
        
        
    def soustraire_requete_travail(self):
        with st.form(key='request_form'):
            st.write('Enter your request below:')
            id = st.text_input('Id de la requete')
            description = st.text_area('Description')
            date_debut = st.date_input('Date de debut')
            submit_button = st.form_submit_button(label='Submit Request')


        ############################## - To edit
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
        ##############################



    def modifier_status_projets(self):
        st.title("Modifier le statut des projets")

        # Fetch current projects
        response = requests.get(f"{API_URL}/projets")
        if response.status_code == 200:
            projets = response.json()
        else:
            st.error("Failed to fetch projects")
            return

        # Display projects and allow selection
        projet_options = {f"{projet['title']} (ID: {projet['id']})": projet['id'] for projet in projets}
        selected_projet = st.selectbox("Sélectionnez un projet à modifier", list(projet_options.keys()))

        if selected_projet:
            projet_id = projet_options[selected_projet]
            new_status = st.selectbox("Nouveau statut", ["Prévu", "En cours", "Terminé"])

            if st.button("Modifier le statut"):
                data = {
                    "projet_id": projet_id,
                    "new_status": new_status
                }
                response = requests.post(f"{API_URL}/modifier_projet", json=data)
                if response.status_code == 200:
                    st.success("Statut du projet mis à jour avec succès")
                    # Define the notification message
                    notification_message = {
                        "title":"Statut du projet mis à jour",
                        "description":"Le projet "+selected_projet+" a été mis à jour avec succès",
                        "date": datetime.now().strftime("%Y-%m-%d"),
                        "isNew":True
                    }

                    # Make the POST request to the envoyer_notification endpoint
                    response2 = requests.post(f"{API_URL}/envoyer_notification", json=notification_message)
                    

                else:
                    st.error("Failed to update project status")
                    st.error(response.text)

    
    def soumettre_projet(self):
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
                    "intervenant_email": st.session_state.get("user_email"),
                    "type_travail": type_travail,
                    "titre": title,
                    "description": description,
                    "date_debut": str(date_debut)
                }
                try:
                    with st.spinner('Sending request...'):
                        response = requests.post(f"{API_URL}/soumettre_projet", json=data)
                    if response.status_code == 200:
                        st.success('Request submitted successfully!')
                        notification_message = {
                            "title":"Nouveau projet soumis",
                            "description":"Un nouveau projet a été soumis par : "+ st.session_state.get("user_email"),
                            "date": datetime.now().strftime("%Y-%m-%d"),
                            "isNew":True
                        }

                        # Make the POST request to the envoyer_notification endpoint
                        response2 = requests.post(f"{API_URL}/envoyer_notification", json=notification_message)
                    else:
                        st.error(f"Failed to submit request: {response.text}")
                except requests.exceptions.RequestException as e:
                    st.error(f"An error occurred: {e}")
                    
    def soumettre_candidature(self):
        st.title("Soumettre une candidature")

        response = requests.get(f"{API_URL}/consulter_infos", params=self.user.to_dict())
        
        if response.status_code == 200:
            travaux = response.json()
        else:
            st.error("Failed to fetch travaux")
            return

        # Display travaux and allow selection
        travail_options = {f"{travail['title']} by {travail['senderEmail']} (ID: {travail['id']})": travail['id']  for travail in travaux}
        
        selected_travail = st.selectbox("Sélectionnez un travail pour soumettre votre candidature", list(travail_options.keys()))

        if selected_travail:
            travail_sender_email =extract_email(selected_travail)
            travail_id = travail_options[selected_travail]
            intervenant_email = st.session_state.get("user_email")

            if st.button("Soumettre candidature"):
                data = {
                    "travail_id": travail_id,
                    "intervenant_email": intervenant_email,
                }
                response = requests.post(f"{API_URL}/soumettre_candidature/candidature", json=data)
                if response.status_code == 200:
                    st.success("Candidature soumise avec succès")
                    
                    notification_message = {
                            "title":"Nouvelle candidature",
                            "description":"Une nouvelle candidature a été soumise par : "+ st.session_state.get("user_email"),
                            "date": datetime.now().strftime("%Y-%m-%d"),
                            "isNew":True,
                            "email": travail_sender_email
                        }

                    # Make the POST request to the envoyer_notification endpoint
                    response2 = requests.post(f"{API_URL}/envoyer_notification/specific", json=notification_message)
                
                else:
                    st.error("Failed to submit candidature")
                    st.error(response.text)
    
        
    def __call__(self):
        self.sidebar()
        if self.selection == "Acceuil":
            self.page_acceuil()
        elif self.selection == "Consulter Travaux":
            self.consulter_travaux()
        elif self.selection == "Soumettre candidature":
            self.soumettre_candidature()
        elif self.selection == "Consulter Profile":
            self.consulter_profile()
        elif self.selection == "Soumettre projet":
            self.soumettre_projet()
        elif self.selection == "Modifier status projet":
            self.modifier_status_projets()
        elif self.selection == "Se deconnecter":
            self.se_deconnecter()
    
    