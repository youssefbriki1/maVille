import streamlit as st
import requests
import logging
from textart import TEXTART
import typing


class menu:
    def __init__(self) -> None:
        self.options = ["Acceuil","Se connecter", "S'inscrire"]
        
    
    def page_acceuil(self):
        st.title("Bienvenue sur MaVille")
        st.write("MaVille est une application qui vous permet de suivre les travaux et les entraves dans votre ville.")
        st.write("Pour continuer, veuillez vous connecter ou vous inscrire.")
        st.write(TEXTART)
        st.write("Veuillez choisir une option:")
        choice = st.radio("Choix", ("Se connecter", "S'inscrire"))
    
    def sidebar(self):
        st.sidebar.title("Navigation")
        selection = st.sidebar.radio("Go to", self.options)
        return selection
    
    def se_connecter(self):
        pass
    
    def s_inscrire(self):
        pass



    def __call__(self, *args: logging.Any, **kwds: logging.Any):
        pass    

