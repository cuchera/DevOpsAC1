import json
import paho.mqtt.client as mqtt
import requests

BROKER = "localhost"
PORT = 1883
TOPIC = "iot/sensores/temperatura"
API_URL = "http://localhost:8080/api/sensores"


def on_message(client, userdata, msg):
    dados = json.loads(msg.payload.decode("utf-8"))

    temperatura = dados["temperatura"]
    status = "ALERTA" if temperatura > 30 else "NORMAL"

    evento = {
        "sensor": dados["sensor"],
        "temperatura": temperatura,
        "unidade": dados["unidade"],
        "status": status
    }

    print("Evento transformado:", evento)

    try:
        resposta = requests.post(
            API_URL,
            json=evento,
            timeout=5
        )
        resposta.raise_for_status()
        print("Evento enviado ao Spring Boot com sucesso!")

    except requests.exceptions.RequestException as erro:
        print("Falha ao enviar evento ao Spring Boot:", erro)


client = mqtt.Client(
    callback_api_version=mqtt.CallbackAPIVersion.VERSION2
)

client.on_message = on_message

client.connect(BROKER, PORT)
client.subscribe(TOPIC)

print("Consumidor MQTT aguardando eventos...")

client.loop_forever()