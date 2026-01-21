from fastapi import FastAPI, HTTPException, Depends
from pydantic import BaseModel
from typing import List, Dict
from sqlalchemy import create_engine, Column, Integer, String
from sqlalchemy.orm import sessionmaker, declarative_base, Session
import json, os
import hashlib

app = FastAPI()

DATABASE_URL = "postgresql://postgres:1234@localhost:5432/valorant_app"

engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()


class User(Base):
    __tablename__ = "users"
    id = Column(Integer, primary_key=True, index=True)
    email = Column(String, unique=True, index=True, nullable=False)
    password_hash = Column(String, nullable=False)

Base.metadata.create_all(bind=engine)


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


#Pydantic модели
class RegisterRequest(BaseModel):
    email: str
    password: str


class LoginRequest(BaseModel):
    email: str
    password: str


class ResponseMessage(BaseModel):
    message: str
    userId: int | None = None


def hash_password(password: str) -> str:
    return hashlib.sha256(password.encode()).hexdigest()


def verify_password(plain_password: str, hashed_password: str) -> bool:
    return hash_password(plain_password) == hashed_password


@app.post("/register", response_model=ResponseMessage)
def register_user(data: RegisterRequest, db: Session = Depends(get_db)):
    existing_user = db.query(User).filter(User.email == data.email).first()
    if existing_user:
        raise HTTPException(status_code=400, detail="User already exists")

    hashed_pw = hash_password(data.password)

    new_user = User(email=data.email, password_hash=hashed_pw)
    db.add(new_user)
    db.commit()
    db.refresh(new_user)

    return {"message": "User registered successfully", "userId": new_user.id}


@app.post("/login", response_model=ResponseMessage)
def login_user(data: LoginRequest, db: Session = Depends(get_db)):
    user = db.query(User).filter(User.email == data.email).first()
    if not user:
        raise HTTPException(status_code=401, detail="User not found")

    if not verify_password(data.password, str(user.password_hash)):
        raise HTTPException(status_code=401, detail="Invalid password")

    return {"message": "Login successful", "userId": user.id}


class RequestData(BaseModel):
    map: str
    team_agents: List[str]
    player_skills: Dict[str, int] = {}


class ResponseData(BaseModel):
    top_agents: List[str]


@app.post("/recommend", response_model=ResponseData)
def recommend_agents(data: RequestData):
    path = f"D:/projects/ai_project/jsonvalorant/{data.map.capitalize()}_agents.json"
    if not os.path.exists(path):
        return {"top_agents": []}

    with open(path, "r", encoding="utf-8") as f:
        map_data = json.load(f)

    agents_stats = map_data.get(data.map.capitalize(), {})
    agents_stats['Kayo'] = agents_stats.pop('KAY/O')
    team_roles = [agents_stats[ag][0] for ag in data.team_agents]

    def winrate(agent_info):
        try:
            return float(agent_info[1].replace("%", ""))
        except:
            return 0

    candidates = {k: v for k, v in agents_stats.items()
                  if k not in data.team_agents and (
                              v[0] not in team_roles or (v[0] == "Duelist" and team_roles.count("Duelist") < 2))}
    top_agents = sorted(candidates.keys(), key=lambda x: winrate(candidates[x]), reverse=True)[:5]
    return {"top_agents": top_agents}


if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="0.0.0.0", port=8000, reload=True)