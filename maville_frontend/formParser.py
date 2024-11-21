import streamlit as st
from pydantic import BaseModel
from typing import Optional
from datetime import datetime


class DataParser:
    @staticmethod
    def formParser(data: dict):
        """
        Parses a dictionary into either an Entrave or Travaux model.
        """
        if "streetid" in data:  # Entrave type
            data["isarterial"] = data["isarterial"].lower() == "t"
            data["length"] = float(data["length"])
            return data
        elif "boroughid" in data:  # Travaux type
            boolean_keys = [
                "duration_days_mon_active", "duration_days_mon_all_day_round",
                "duration_days_tue_active", "duration_days_tue_all_day_round",
                "duration_days_wed_active", "duration_days_wed_all_day_round",
                "duration_days_thu_active", "duration_days_thu_all_day_round",
                "duration_days_fri_active", "duration_days_fri_all_day_round",
                "duration_days_sat_active", "duration_days_sat_all_day_round",
                "duration_days_sun_active", "duration_days_sun_all_day_round"
            ]
            for key in boolean_keys:
                data[key] = data[key].lower() == "t"
            data["longitude"] = float(data["longitude"])
            data["latitude"] = float(data["latitude"])
            data["duration_start_date"] = datetime.fromisoformat(data["duration_start_date"].replace("Z", "+00:00"))
            data["duration_end_date"] = datetime.fromisoformat(data["duration_end_date"].replace("Z", "+00:00"))
            data["load_date"] = datetime.fromisoformat(data["load_date"].replace("Z", "+00:00"))
            return data
        else:
            raise ValueError("Unrecognized data format")

    @staticmethod
    def write(data, **kwargs):
        """
        Writes the parsed data 
        """
        for key, value in data.items():
            st.write(f"{key}: {value}")

        if kwargs:
            for key, value in kwargs.items():
                st.write(f"{key}: {value}")
