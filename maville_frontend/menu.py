import streamlit as st
import requests
import logging
from util_text import TEXTART, API_URL
import typing
from datetime import datetime
from streamlit_js_eval import streamlit_js_eval


class Menu:
    def __init__(self) -> None:
        self.selection = None
        self.options = ["Acceuil","Se connecter", "S'inscrire"]
        self.logger = logging.getLogger(__name__)
        self.API_URL = "http://localhost:8080/api"
        
    
    def page_acceuil(self):
        st.title("🌟 Bienvenue sur l'Application MaVille🌟")
        st.markdown(
            """
            **Explorez les fonctionnalités de cette application moderne conçue pour la gestion de vos données urbaines.**  
            Suivez vos projets, analysez les impacts, et restez informé en temps réel. 🚀
            """
        )


        st.markdown(f"📅 **Date et heure actuelles :** {datetime.now().strftime('%d %B %Y %H:%M')}")


        st.subheader("✨ Points forts :")
        st.markdown(
            """
            ✅ Interface utilisateur simple et élégante.  
            ✅ Performances optimisées pour de grands ensembles de données.  
            ✅ Intégration avec des outils modernes.  
            ✅ Expérience personnalisée pour répondre à vos besoins.
            """
        )

        st.markdown("---")
        st.markdown(
            """
            **Prêt à commencer ?**  
            ➡️ Naviguez via le menu sur le côté gauche pour explorer les fonctionnalités.  
            """
        )
        st.markdown("💡 *Votre feedback est toujours le bienvenu pour améliorer l'application.*")

        st.balloons()
    
    def sidebar(self):
        st.sidebar.title("Navigation")
        self.selection = st.sidebar.radio("Aller à", self.options)
    
    def s_inscrire(self):
        with st.form(key='signup_form'):
            st.write("Entrez vos informations ci-dessous :\n* Obligatoires pour les résidents")
            first_name = st.text_input("Prénom*")
            last_name = st.text_input("Nom*")
            email = st.text_input("Email*")
            phone = st.text_input("Numéro de téléphone")
            address = st.text_input("Adresse")
            postal_code = st.text_input("Code postal")
            birth_date = st.date_input("Date de naissance", min_value=datetime(year=1900, month=1, day=1))
            role = st.radio("Rôle", ("Intervenant", "Resident"))
            password = st.text_input("Mot de passe*", type="password")
            retyped_password = st.text_input("Confirmer le mot de passe*", type="password")

            if st.form_submit_button(label="S'inscrire"):
                # Validation
                erreurs = []
                if not first_name.strip():
                    erreurs.append("Le prénom est obligatoire.")
                if not last_name.strip():
                    erreurs.append("Le nom est obligatoire.")
                if not email.strip():
                    erreurs.append("L'email est obligatoire.")
                if role == "Resident":
                    if not address.strip():
                        erreurs.append("L'adresse est obligatoire pour les résidents.")
                    if not postal_code.strip():
                        erreurs.append("Le code postal est obligatoire pour les résidents.")
                    if not birth_date:
                        erreurs.append("La date de naissance est obligatoire pour les résidents.")
                if password != retyped_password:
                    erreurs.append("Les mots de passe ne correspondent pas.")

                if erreurs:
                    for erreur in erreurs:
                        st.error(erreur)
                else:
                    data = {
                        "name": f"{first_name} {last_name}",
                        "email": email,
                        "phone": phone,
                        "address": address if role == "Resident" else None,
                        "postal_code": postal_code if role == "Resident" else None,
                        "birth_date": str(birth_date) if role == "Resident" else None,
                        "type": None if role == "Intervenant" else None,
                        "id_city": None if role == "Resident" else None,
                        "role": role,
                        "password": password,
                        'horraires': {}
                    }
                    # Log and Submit Data
                    self.logger.info(data)
                    response = requests.post(f"{API_URL}/signup", json=data)
                    if response.status_code == 200:
                        self.logger.info("Succès")
                        st.success("Création de compte réussie !")
                    else:
                        st.error("Une erreur s'est produite. Veuillez réessayer.")

    
    def se_connecter(self):
        st.title("Se connecter")
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
                    with st.spinner('Se connecte...'):
                        response = requests.post(f"{API_URL}/login", json=data)
                    if response.status_code == 200:
                        result = response.json()  
                        st.session_state['loged_in'] = True
                        st.session_state['user_email'] = email
                        st.session_state['user_role'] = role
                        st.session_state['Notification_number'] = result["notificationsNumber"]
                        st.success('Connexion reussie!')
                        #streamlit_js_eval(js_expressions="parent.window.location.reload()")
                        #self.page_acceuil()

                    elif response.status_code == 401:
                        st.error("Invalid email or password.")
                    else:
                        st.error(f"Login failed: {response.text}")
                except requests.exceptions.RequestException as e:
                    st.error(f"An error occurred: {e}")
                        
    
    def se_deconnecter(self):
        #print(st.session_state)
        st.session_state = {'user_role': '', 'user_email': '', 'loged_in': False}
        st.write("Vous etes deconnecte")
        streamlit_js_eval(js_expressions="parent.window.location.reload()")

    def __call__(self):
        self.sidebar()
        if self.selection == "Acceuil":
            self.page_acceuil()
        elif self.selection == "Se connecter":
            self.se_connecter()
        elif self.selection == "S'inscrire":
            self.s_inscrire()
            

