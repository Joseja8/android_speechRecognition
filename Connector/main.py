from pyfirmata import Arduino, time
import nltk
from nltk.tokenize import TreebankWordTokenizer
import sys

board = Arduino('/dev/ttyACM0')

nltk.data.path.append("/home/joseja/Documents/nltk_data")
tokenizer = TreebankWordTokenizer()


def parse_input_tokens(input_tokens):
    task = None
    pins = []

    if 'encender' in input_tokens:
        task = 1
    elif 'apagar' in input_tokens:
        task = 0

    if '0' in input_tokens or 'cero' in input_tokens:
        pins.append(0)
    if '1' in input_tokens  or 'uno' in input_tokens:
        pins.append(1)
    if '2' in input_tokens  or 'dos' in input_tokens:
        pins.append(2)
    if '3' in input_tokens  or 'tres' in input_tokens:
        pins.append(3)
    if '4' in input_tokens  or 'cuatro' in input_tokens:
        pins.append(4)
    if '5' in input_tokens  or 'cinco' in input_tokens:
        pins.append(5)
    if '6' in input_tokens  or 'seis' in input_tokens:
        pins.append(6)
    if '7' in input_tokens  or 'siete' in input_tokens:
        pins.append(7)
    if '8' in input_tokens  or 'ocho' in input_tokens:
        pins.append(8)
    if '9' in input_tokens  or 'nueve' in input_tokens:
        pins.append(9)

    if len(pins) == 0:
        pins = None

    return task, pins

def main(argv):

    try:
        input_text = sys.argv[1]
        input_tokens = tokenizer.tokenize(input_text)
        task, pins = parse_input_tokens(input_tokens)
    except:
        print('State not found, default is 0')
        state = 0

    if task is not None and pins is not None:
        for pin in pins:
            board.digital[pin].write(task)
    else:
        print('Orden no reconocida')


if __name__ == "__main__":
    main(0)
