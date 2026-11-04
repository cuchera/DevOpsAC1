from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI(title="AC1 AI Service")


class RecommendationRequest(BaseModel):
    studentId: int
    studentName: str
    finalGrade: float


class AssistantRequest(BaseModel):
    studentId: int
    question: str


@app.get("/health")
def health() -> dict[str, str]:
    return {"status": "ok"}


@app.post("/recommendations")
def recommendation(request: RecommendationRequest) -> dict[str, str]:
    if request.finalGrade >= 7:
        content = (
            f"{request.studentName}, recomendamos um curso avançado "
            "relacionado à sua evolução."
        )
    else:
        content = (
            f"{request.studentName}, recomendamos revisar os fundamentos "
            "antes de avançar."
        )
    return {"content": content}


@app.post("/assistant")
def assistant(request: AssistantRequest) -> dict[str, str]:
    return {
        "content": (
            "Resposta demonstrativa do assistente educacional: "
            f"recebi sua pergunta sobre '{request.question}'."
        )
    }
