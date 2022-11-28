from fastapi import FastAPI
from pydantic import BaseModel
from typing import List
from uuid import uuid4

app = FastAPI()
students = []


class Student(BaseModel):
    id: str
    name: str
    lastname: str
    skills: list[str] = []


# Para traer todos los estudiantes
@app.get("/students")
def getAllStudents():
    return students

# Para guardar un estudiante
@app.post("/students")
def saveStudent(s: Student):
    s.id = str(uuid4())  # Esto le da un id Random
    students.append(s.dict())  # Esto es para guardar el Objeto (estudiante en este caso)
    return ("Estudiante guardado")

# Buscar un estudiante por ID
@app.get("/students/{id}")
def get_student(id:str):
    for student in students:
        if student["id"] == id:
            return student
    return ("No existe el estudiante")

#Actualiza un estudiante buscado por ID
@app.put("/students/{id}")
def updateStudent(s:Student,id:str):
    for student in students:
        if student["id"] == id:
            student["name"] = s.name
            student["lastname"] = s.lastname
            student["skills"] = s.skills
        return ("estudiante modificado")
    return ("No existe el estudiante")

#Borrar un estudiante buscado por ID
@app.delete("/students/id")
def deleteStudent(id:str):
    for student in students:
        if student["id"] == id:
            students.remove(student)
            return  ("Estudiante eliminado")
    return ("No se ha encontrado al estudiante")
