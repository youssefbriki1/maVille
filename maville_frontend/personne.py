from pydantic import BaseModel

class Personne(BaseModel):
    email: str
    role: str