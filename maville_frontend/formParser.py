import streamlit as st
from pydantic import BaseModel
from typing import Optional
from datetime import datetime
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
                data["isarterial"] = data["isarterial"].lower() == "t"
                data["length"] = float(data["length"])
                output.append(data)
            return output
        elif "boroughid" in data_list[0]:  # Travaux type
            for data in data_list:
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
                output.append(data)
            #pp.pprint(output)
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

        if kwargs:
            for key, value in kwargs.items():
                st.write(f"{key}: {value}")
