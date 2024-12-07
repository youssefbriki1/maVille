TEXTART="""

             ______  _______          ____    ____      ____  ____  ____         ____             ______   
            |      \\/       \\    ____|\\   \\  |    |    |    ||    ||    |       |    |        ___|\\     \\  
           /          /\\     \\  /    /\\    \\ |    |    |    ||    ||    |       |    |       |     \\     \\ 
          /     /\\   / /\\     ||    |  |    ||    |    |    ||    ||    |       |    |       |     ,_____/|
         /     /\\ \\_/ / /    /||    |__|    ||    |    |    ||    ||    |  ____ |    |  ____ |     \\--'\\_|/
        |     |  \\|_|/ /    / ||    .--.    ||    |    |    ||    ||    | |    ||    | |    ||     /___/|  
        |     |       |    |  ||    |  |    ||\\    \\  /    /||    ||    | |    ||    | |    ||     \\____|\\ 
        |\\____\\       |____|  /|____|  |____|| \\ ___\\/___ / ||____||____|/____/||____|/____/||____ '     /|
        | |    |      |    | / |    |  |    | \\ |   ||   | / |    ||    |     |||    |     |||    /_____/ |
         \\|____|      |____|/  |____|  |____|  \\|___||___|/  |____||____|_____|/|____|_____|/|____|     | /
            \\(          )/       \\(      )/      \\(    )/      \\(    \\(    )/     \\(    )/     \\( |_____|/ 
             '          '         '      '        '    '        '     '    '       '    '       '    )/    
                                                                                                     '      
"""

import os

API_URL = os.environ.get("BACKEND_URL", "http://localhost:8080")


TYPES_TRAVAIL = [    "Travaux routiers",
    "Travaux de gaz ou électricité",
    "Construction ou rénovation",
    "Entretien paysager",
    "Travaux liés aux transports en commun",
    "Travaux de signalisation et éclairage",
    "Travaux souterrains",
    "Travaux résidentiels",
    "Entretien urbain",
    "Entretien des réseaux de télécommunication"
]

BOOLEAN_KEYS = [
    "duration_days_mon_active", "duration_days_tue_active",
    "duration_days_wed_active", "duration_days_thu_active",
    "duration_days_fri_active", "duration_days_sat_active",
    "duration_days_sun_active"
]


BLOCKED_TYPES = {
    "stmimpact_blockedtype": "🚍 Impact STM",
    "reservedlane_blockedtype": "🚧 Voie réservée",
    "streetimpacttype": "🚦 Type d'impact",
    "backsidewalk_blockedtype": "🚶 Trottoir arrière",
    "bikepath_blockedtype": "🚴 Piste cyclable",
    "sidewalk_blockedtype": "🚶 Trottoir avant",
    "otherproviderimpact_blockedtype": "📡 Impact autre fournisseur",
}
