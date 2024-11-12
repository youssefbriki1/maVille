import pprint
import streamlit as st
import requests

API_URL = "http://localhost:8080/api"
loged_in = False

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
                pprint.pprint(data)
                response = requests.post(f"{API_URL}/signup", json=data)
                if response.status_code == 200:
                    print("success")
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
            print(data)
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
        

def main():
    global loged_in
    st.set_page_config(page_title='Maville', page_icon='🏙️', layout='wide')

    # Header
    st.title('Bienvenue a Maville')
    connected_text = f"Connecte en tant " # To update later
    st.subheader('Explore Your City Like Never Before')

    # Sidebar Navigation
    st.sidebar.title('Navigation')
    selection = st.sidebar.radio("Go to", ["Home", "About", "Contact", "Creer un compte", f'{"Envoyer requete de travail" if st.session_state.get("loged_in") else "Connexion"}'])

    # Home Page
    if selection == "Home":
        st.header("Home")
        st.write("Discover the best places and events happening in your city.")
        # Add more content here

    # About Page
    elif selection == "About":
        st.header("About Maville")
        st.write("""
        Maville is your ultimate guide to exploring the hidden gems of your city.
        Our mission is to help you discover new experiences and connect with your community.
        """)
        # Add more content here

    # Contact Page
    elif selection == "Contact":
        st.header("Contact Us")
        st.write("We'd love to hear from you! Reach out to us through any of the channels below:")
        st.markdown("""
        - **Email:** contact@maville.com
        - **Phone:** +1 234 567 890
        - **Address:** 123 Main Street, Anytown, USA
        """)
        
        
    elif selection == "Creer un compte":
        st.header("Sign Up")
        sign_up()
        
    elif selection == "Connexion":
        st.header("Sign In")
        log_in()
        
    print(st.session_state)   # Where stored everything     

if __name__ == '__main__':
    main()
