import streamlit as st
import requests
import logging
from util_text import *
from menu import Menu
from menu_resident import Menu_Resident
from personne import Personne



logger = logging.getLogger(__name__)
loged_in = False
TYPES_TRAVAIL = [    "Travaux routiers",
    "Travaux de gaz ou électricité",
    "Construction ou rénovation",
    "Entretien paysager",
    "Travaux liés aux transports en commun",
    "Travaux de signalisation et éclairage",
    "Travaux souterrains",
    "Travaux résidentiels",
    "Entretien urbain",
    "Entretien des réseaux de télécommunication"
]

def sign_up():
    # Sign-up form for Resident or Intervenant
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
        
        # Check for matching passwords
        if password != retyped_password:
            st.warning('Passwords do not match')

        id_city = None
        # Process submission
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
                logger.info(data)
                response = requests.post(f"{API_URL}/signup", json=data)
                if response.status_code == 200:
                    logger.info("success")
                    st.success('Connexion reussie!')
                # TODO: Process data (e.g., send to backend)
            else:
                st.error('Please make sure passwords match.')
        
        
def log_in():
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
            logger.info(data)
            try:
                # Show a loading spinner while the request is being processed
                with st.spinner('Authenticating...'):
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
        


def consulter_travaux(role):
    # Send 'user' instead of 'role' in params
    user = {"user": role}
    response = requests.get(f"{API_URL}/consulter_infos", params=user)
    
    if response.status_code == 200:
        requests_data = response.json()
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
        
def envoyer_requete_resident():
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
                
        
    
def main():
    global loged_in
    st.set_page_config(page_title='Maville', page_icon='🏙️', layout='wide')

    # Header
    if st.session_state.get("loged_in"):
        user = Personne(email=st.session_state.get("user_email"), role=st.session_state.get("user_role"))
        connected_text = f'Connecte en tant {user.email}, {user.role}' 
        st.write(connected_text)
        if user.role == "Resident":
            menu = Menu_Resident(user)
            menu()
        else:
            pass

    # Sidebar Navigation
    else:
        menu = Menu()
        menu()
            
    logger.info(st.session_state)   # Where stored everything     

main()

