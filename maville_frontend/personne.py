#from pydantic import BaseModel

class Personne():
    def __init__(self, email: str, role: str) -> None:
        self.email = email
        self.role = role


    def __str__(self):
        return f"Email: {self.email}, Role: {self.role}"
    
    def __repr__(self):
        return str(self)
    
    def to_dict(self):
        """Converts the object to a dictionary."""
        return {"email": self.email, "role": self.role}
    