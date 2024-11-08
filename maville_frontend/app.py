import streamlit as st


def sign_up():
    #TODO: Add Information
    with st.form(key='signup_form'):
        st.write('Enter your information below:')
        first_name = st.text_input('First Name')
        last_name = st.text_input('Last Name')
        email = st.text_input('Email')
        st.radio('Role', ('Intervenant', 'Resident'))
        password = st.text_input('Password', type='password')
        retyped_password = st.text_input('Retype Password', type='password')
        if password != retyped_password:
            st.warning('Passwords do not match')
        submit_button = st.form_submit_button(label='Sign Up', disabled=(password != retyped_password))
        
        
def sign_in():
    with st.form(key='signin_form'):
        st.write('Enter your information below:')
        email = st.text_input('Email')
        password = st.text_input('Password', type='password')
        submit_button = st.form_submit_button(label='Sign In')
        
        

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
        sign_in()
        

if __name__ == '__main__':
    main()
