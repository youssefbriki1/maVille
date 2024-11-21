from menu import Menu
import streamlit as st
import requests
import logging
from util_text import API_URL,TYPES_TRAVAIL
from personne import Personne


def Menu_Intervenant(Menu):
    def __init__(self,user:Personne) -> None:
        super().__init__()
        self.options = ["Acceuil","Consulter travaux", "Consulter Profile", "Se deconnecter"]    
        self.user = user
    
    