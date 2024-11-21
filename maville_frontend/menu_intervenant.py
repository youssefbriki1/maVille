from menu import Menu
import streamlit as st
import requests
import logging
from util_text import API_URL,TYPES_TRAVAIL
from personne import Personne


class Menu_Intervenant(Menu):
    def __init__(self,user:Personne) -> None:
        super().__init__()
        self.options = ["Acceuil","Consulter Travaux", "Consulter Profile", "Se deconnecter"]    
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
    
    def se_deconnecter(self):
        self.user = None
        st.write("Vous etes deconnecte")
        
    def __call__(self):
        self.sidebar()
        if self.selection == "Acceuil":
            st.title("Bienvenue "+self.user.email)
        elif self.selection == "Consulter Travaux":
            self.consulter_travaux()
        elif self.selection == "Consulter Profile":
            self.consulter_profile()
        elif self.selection == "Se deconnecter":
            self.se_deconnecter()
    
    