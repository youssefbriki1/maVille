import streamlit as st
import logging 
import requests
from menu import Menu
from personne import Personne
from util_text import API_URL, TYPES_TRAVAIL


class Menu_Resident(Menu):
    def __init__(self,user:Personne) -> None:
        super().__init__()
        self.options = ["Acceuil","Consulter Entraves", "Consulter Travaux", "Consulter travaux residentiels", "Soumettre requete travail","Consulter Profile", "Se deconnecter"]    
        self.user = user
        
        
        
    def __fetch_api_data(self, user_choice):
        curr_data = {"choix": user_choice}
        response = requests.get(f"{API_URL}/fetch-data", params=curr_data)
        st.write(response.json())

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
        # Create a form to submit a request
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
                    # Show a loading spinner while the request is being processed
                    with st.spinner('Sending request...'):
                        response = requests.post(f"{API_URL}/envoyer_requete_resident", json=data)
                    # Handle the response
                    if response.status_code == 200:
                        st.success('Request submitted successfully!')
                    else:
                        st.error(f"Failed to submit request: {response.text}")
                except requests.exceptions.RequestException as e:
                    st.error(f"An error occurred: {e}")

        
            
    def se_deconnecter(self):
        self.user = None
        st.write("Vous etes deconnecte")
        #raise Exception("Deconnexion")
    
        
    def __call__(self):
        self.sidebar()
        if self.selection == "Acceuil":
            st.title("Bienvenue "+self.user.email)
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
        elif self.selection == "Se deconnecter":
            self.se_deconnecter()
