import streamlit as st
from pydantic import BaseModel
from typing import Optional
from datetime import datetime
from util_text import BLOCKED_TYPES, BOOLEAN_KEYS
#import pprint as pp
from typing import List
class DataParser:
    @staticmethod
    def formParser(data_list:List[dict]) -> List[dict]:
        """
        Parses a dictionary into either an Entrave or Travaux model.
        """
        output = []
        if "streetid" in data_list[0]:  # Entrave type
            for data in data_list:
                prettified_data = {}

                # Map keys to French labels with emojis
                prettified_data["📛 Nom de la rue"] = data.get("name", "Inconnu")
                prettified_data["🔢 ID de la demande"] = data.get("id_request", "Inconnu")
                prettified_data["🔀 De"] = data.get("fromname", "Inconnu")
                prettified_data["🔀 À"] = data.get("toname", "Inconnu")
                prettified_data["🚗 Largeur de l'impact sur la rue"] = data.get("streetimpactwidth", "Inconnu")
                prettified_data["🛣️ Longueur (mètres)"] = data.get("length", "Inconnu")
                prettified_data["🅿️ Places de stationnement libre"] = data.get("nbfreeparkingplace", "Inconnu")
                prettified_data["🚦 Rue artérielle"] = "Oui" if data.get("isarterial", False) else "Non"


                for key, label in BLOCKED_TYPES.items():
                    prettified_data[label] = data.get(key, "Aucun impact / non applicable").capitalize()

                output.append(prettified_data)

            return output
        elif "boroughid" in data_list[0]:  # Travaux type
            output = []

            for data in data_list:
                
                prettified_data = {}
                prettified_data["🏙️ Arrondissement"] = data["boroughid"]
                prettified_data["📍 Latitude"] = float(data["latitude"])
                prettified_data["📍 Longitude"] = float(data["longitude"])
                prettified_data["🕒 Date de début"] = datetime.fromisoformat(
                    data["duration_start_date"].replace("Z", "+00:00")
                ).strftime("%d %B %Y %H:%M")
                prettified_data["🕒 Date de fin"] = datetime.fromisoformat(
                    data["duration_end_date"].replace("Z", "+00:00")
                ).strftime("%d %B %Y %H:%M")
                prettified_data["📅 Date de chargement"] = datetime.fromisoformat(
                    data["load_date"].replace("Z", "+00:00")
                ).strftime("%d %B %Y %H:%M")
                prettified_data["📝 Raison"] = data.get("reason_category", "N/A").capitalize()
                prettified_data["🛠️ Organisation"] = data.get("organizationname", "Inconnu")
                prettified_data["🔢 Numéro de contrat"] = data.get("contractnumber", "N/A")

                for key in BOOLEAN_KEYS:
                    pretty_key = key.replace("duration_days_", "").replace("_active", "").capitalize()
                    french_key = {
                        "mon": "Lundi",
                        "tue": "Mardi",
                        "wed": "Mercredi",
                        "thu": "Jeudi",
                        "fri": "Vendredi",
                        "sat": "Samedi",
                        "sun": "Dimanche"
                    }.get(pretty_key.lower(), pretty_key)
                    prettified_data[f"{french_key}"] = "✅ Oui" if data[key] =='t' else " ❌ Non"

                # Add prettified entry to output
                output.append(prettified_data)

            return output
        else:
            raise ValueError("Unrecognized data format")

    @staticmethod
    def write(data:List[dict], **kwargs):
        """
        Writes the parsed data 
        """
        for data in data:
            for key, value in data.items():
                st.write(f"{key}: {value}")
            st.write('---')

        if kwargs:
            for key, value in kwargs.items():
                st.write(f"{key}: {value}")
