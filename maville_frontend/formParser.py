import streamlit as st
import requests
import logging
import json
from pydantic import BaseModel
import typing

# To parse everything from the json response 
def formParser(Basemodel):
    pass


    """ Entraves
    
    
    
          {
        "_id": 2,
        "id_request": "671a580d7649be00197b3eee",
        "streetid": "rue Garnier ",
        "streetimpactwidth": "3-6 mètres",
        "streetimpacttype": "Rue barrée",
        "nbfreeparkingplace": "0",
        "sidewalk_blockedtype": "Obstrué (un passage piéton de 1,5 m est conservé)",
        "backsidewalk_blockedtype": "Barré",
        "bikepath_blockedtype": "Aucun impact / non applicable",
        "name": "rue Garnier ",
        "shortname": "Garnier",
        "fromname": "rue Linden ",
        "fromshortname": "Linden",
        "toname": "rue Fleury Est ",
        "toshortname": "Fleury",
        "length": "90.68",
        "isarterial": "f",
        "stmimpact_blockedtype": "Aucun impact / non applicable",
        "otherproviderimpact_blockedtype": "Aucun impact / non applicable",
        "reservedlane_blockedtype": "Aucun impact / non applicable"
      },


    """
    
    
    """ Travaux
    
        "_id": 1,
        "id": "671f92f4d2c76f001aa8abfc",
        "permit_permit_id": "OCC-2410OK24917841",
        "contractnumber": null,
        "boroughid": "Le Plateau-Mont-Royal",
        "permitcategory": "Travaux",
        "currentstatus": "Permis émis",
        "duration_start_date": "2024-11-03T19:00:00Z",
        "duration_end_date": "2024-11-22T18:59:59Z",
        "reason_category": "Construction/rénovation sans excavation",
        "occupancy_name": "Boyer entre Mont-Royal et Généreux",
        "submittercategory": "Entreprise",
        "organizationname": "Demonfort Maconnerie inc.",
        "duration_days_mon_active": "t",
        "duration_days_mon_all_day_round": "t",
        "duration_days_tue_active": "t",
        "duration_days_tue_all_day_round": "t",
        "duration_days_wed_active": "t",
        "duration_days_wed_all_day_round": "t",
        "duration_days_thu_active": "t",
        "duration_days_thu_all_day_round": "t",
        "duration_days_fri_active": "t",
        "duration_days_fri_all_day_round": "t",
        "duration_days_sat_active": "t",
        "duration_days_sat_all_day_round": "t",
        "duration_days_sun_active": "t",
        "duration_days_sun_all_day_round": "t",
        "duration_days_sat_start_time": null,
        "duration_days_sat_end_time": null,
        "duration_days_mon_start_time": null,
        "duration_days_mon_end_time": null,
        "duration_days_tue_start_time": null,
        "duration_days_tue_end_time": null,
        "duration_days_wed_start_time": null,
        "duration_days_wed_end_time": null,
        "duration_days_thu_start_time": null,
        "duration_days_thu_end_time": null,
        "duration_days_fri_start_time": null,
        "duration_days_fri_end_time": null,
        "duration_days_sun_start_time": null,
        "duration_days_sun_end_time": null,
        "load_date": "2024-10-31T00:01:24Z",
        "longitude": "-73.58009377988336",
        "latitude": "45.52741115256457"

    
    """