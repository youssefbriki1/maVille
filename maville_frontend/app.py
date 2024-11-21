import streamlit as st
import requests
import logging
from util_text import *
from menu import Menu
from menu_resident import Menu_Resident
from personne import Personne
from menu_intervenant import Menu_Intervenant



logger = logging.getLogger(__name__)
loged_in = False
    
def main():
    global loged_in
    st.set_page_config(page_title='Maville', page_icon='🏙️', layout='wide')

    # Header
    if st.session_state.get("loged_in"):
        #print(st.session_state)
        user = Personne(email=st.session_state.get("user_email"), role=st.session_state.get("user_role"))
        connected_text = f'Connecte en tant {user.email}, {user.role}' 
        st.write(connected_text)
        if user.role == "Resident":
            menu = Menu_Resident(user)
            menu()
        else:
            menu  = Menu_Intervenant(user)
            menu()

    # Sidebar Navigation
    else:
        menu = Menu()
        menu()
            
    logger.info(st.session_state)   # Where stored everything     

main()

