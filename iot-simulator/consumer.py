
import paho.mqtt.client as mqtt

BROKER = "localhost"
PORT = 1883
TOPIC = "iot/sensores/temperatura"


def on_message(client, userdata, msg):
    mensagem = msg.payload.decode("utf-8")
    print("Evento recebido:", mensagem)


client = mqtt.Client(
    callback_api_version=mqtt.CallbackAPIVersion.VERSION2
)

client.on_message = on_message

client.connect(BROKER, PORT)
client.subscribe(TOPIC)

print("Consumidor MQTT aguardando eventos...")

client.loop_forever()
