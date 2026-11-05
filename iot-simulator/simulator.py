
import json
import random
import time
import paho.mqtt.client as mqtt

client = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
client.connect("localhost", 1883, 60)
client.loop_start()

try:
    while True:
        evento = {
            "sensor": "temperatura-01",
            "temperatura": round(random.uniform(20, 35), 2),
            "unidade": "C"
        }

        client.publish("iot/sensores/temperatura", json.dumps(evento))
        print("Evento enviado:", evento)

        time.sleep(5)

except KeyboardInterrupt:
    print("Simulador encerrado.")

finally:
    client.disconnect()
    client.loop_stop()
