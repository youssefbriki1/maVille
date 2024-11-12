import streamlit as st
import requests

API_URL = "http://localhost:8080/api"

def sign_up():
    # Sign-up form for Resident or Intervenant
    with st.form(key='signup_form'):
        st.write('Enter your information below:')
        
        # Collect user information
        first_name = st.text_input('First Name')
        last_name = st.text_input('Last Name')
        email = st.text_input('Email')
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
                response = requests.post(f"{API_URL}/signup", json=data)
                if response.status_code == 200:
                    print("success")
                    st.success('Connexion reussie!')
                # TODO: Process data (e.g., send to backend)
            else:
                st.error('Please make sure passwords match.')
        
        
def log_in():
    #response = requests.get(f"{API_URL}/login")
    with st.form(key='signin_form'):
        st.write('Enter your information below:')
        email = st.text_input('Email')
        password = st.text_input('Password', type='password')
        role = st.radio('Role', ('Intervenant', 'Resident'))
        data = {
            "email": email,
            "password": password,
            "role": role
        }
        submit_button = st.form_submit_button(label='Sign In')
        if submit_button:
            response = requests.post(f"{API_URL}/login", json=data)
            if response.status_code == 200:
                st.success('Sign-in successful!')
        
        

def main():
    st.set_page_config(page_title='Maville', page_icon='🏙️', layout='wide')

    # Header
    st.title('Welcome to Maville')
    st.subheader('Explore Your City Like Never Before')

    # Sidebar Navigation
    st.sidebar.title('Navigation')
    selection = st.sidebar.radio("Go to", ["Home", "About", "Contact", "Sign Up", "Sign In"])

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
        
        
    elif selection == "Sign Up":
        st.header("Sign Up")
        sign_up()
        
    elif selection == "Sign In":
        st.header("Sign In")
        log_in()
        

if __name__ == '__main__':
    main()
