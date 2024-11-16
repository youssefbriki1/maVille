import streamlit as st
import requests
import logging
from util_text import TEXTART
import typing
from util_text import API_URL


class Menu:
    def __init__(self) -> None:
        self.selection = None
        self.options = ["Acceuil","Se connecter", "S'inscrire"]
        self.logger = logging.getLogger(__name__)
        self.API_URL = "http://localhost:8080/api"
        
    
    def page_acceuil(self):
        st.title("Bienvenue sur MaVille")
        st.write("MaVille est une application qui vous permet de suivre les travaux et les entraves dans votre ville.")
        st.write("Pour continuer, veuillez vous connecter ou vous inscrire.")
    
    def sidebar(self):
        st.sidebar.title("Navigation")
        self.selection = st.sidebar.radio("Go to", self.options)
    
    def s_inscrire(self):
        with st.form(key='signup_form'):
            st.write('Enter your information below: \n * Sont obligatoires pour residents')
            
            # Collect user information
            first_name = st.text_input('First Name*')
            last_name = st.text_input('Last Name*')
            email = st.text_input('Email*')
            phone = st.text_input('Phone Number')
            address = st.text_input('Address')  # Specific to Residents
            postal_code = st.text_input('Postal Code')  # Specific to Residents
            birth_date = st.date_input('Birth Date')  # Specific to Residents
            role = st.radio('Role', ('Intervenant', 'Resident'))
            password = st.text_input('Password', type='password')
            retyped_password = st.text_input('Retype Password', type='password')
            
            if password != retyped_password:
                st.warning('Passwords do not match')

            id_city = None
            if st.form_submit_button(label='Sign Up'):
                if password == retyped_password:
                    data = {
                        "name": f"{first_name} {last_name}",
                        "email": email,
                        "phone": phone,
                        "address": address if role == 'Resident' else None,
                        "postal_code": postal_code if role == 'Resident' else None,
                        "birth_date": str(birth_date) if role == 'Resident' else None,
                        "type": None if role == 'Intervenant' else None,
                        "id_city": id_city if role == 'Intervenant' else None,
                        "role": role,
                        "password": password
                    }
                    self.logger.info(data)
                    response = requests.post(f"{API_URL}/signup", json=data)
                    if response.status_code == 200:
                        self.logger.info("success")
                        st.success('Creation de compte reussie!')
                else:
                    st.error('Please make sure passwords match.')

    
    def se_connecter(self):
        st.title("Login")
        # Create a login form
        with st.form(key='signin_form'):
            st.write('Enter your information below:')
            email = st.text_input('Email')
            password = st.text_input('Password', type='password')
            role = st.radio('Role', ('Intervenant', 'Resident'))
            submit_button = st.form_submit_button(label='Sign In')

        if submit_button:
            if not email or not password:
                st.warning("Please enter both email and password.")
            else:
                data = {
                    "email": email,
                    "password": password,
                    "role": role
                }
                self.logger.info(data)
                try:
                    # Show a loading spinner while the request is being processed
                    with st.spinner('Se connecte...'):
                        response = requests.post(f"{API_URL}/login", json=data)
                    # Handle the response
                    if response.status_code == 200:
                        st.session_state['loged_in'] = True
                        st.session_state['user_email'] = email
                        st.session_state['user_role'] = role
                        st.success('Sign-in successful!')
                    elif response.status_code == 401:
                        st.error("Invalid email or password.")
                    else:
                        st.error(f"Login failed: {response.text}")
                except requests.exceptions.RequestException as e:
                    st.error(f"An error occurred: {e}")



    def __call__(self):
        self.sidebar()
        if self.selection == "Acceuil":
            self.page_acceuil()
        elif self.selection == "Se connecter":
            self.se_connecter()
        elif self.selection == "S'inscrire":
            self.s_inscrire()
            

