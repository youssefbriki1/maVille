import streamlit as st
import pydantic
import logging
import typing



class menu_resident(BaseModel):
    email: str
    role:str
