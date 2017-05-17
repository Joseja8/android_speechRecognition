from pyfirmata import Arduino, time
import nltk
import sys

board = Arduino('/dev/ttyACM0')

nltk.data.path.append("/home/joseja/Documents/nltk_data")
tokenizer = nltk.TreebankWordTokenizer()


def parse_input_tokens(input_tokens):
    task = None
    pin = None
    if 'encender' in input_tokens:
        task = 1
    elif 'apagar' in input_tokens:
        task = 0

    if '0' in input_tokens or 'cero' in input_tokens:
        return task, 0
    elif '1' in input_tokens  or 'uno' in input_tokens:
        return task, 1
    elif '2' in input_tokens  or 'dos' in input_tokens:
        return task, 2
    elif '3' in input_tokens  or 'tres' in input_tokens:
        return task, 3
    elif '4' in input_tokens  or 'cuatro' in input_tokens:
        return task, 4
    elif '5' in input_tokens  or 'cinco' in input_tokens:
        return task, 5
    elif '6' in input_tokens  or 'seis' in input_tokens:
        return task, 6
    elif '7' in input_tokens  or 'siete' in input_tokens:
        return task, 7
    elif '8' in input_tokens  or 'ocho' in input_tokens:
        return task, 8
    elif '9' in input_tokens  or 'nueve' in input_tokens:
        return task, 9

    return task, pin

def main(argv):

    try:
        input_text = sys.argv[1]
        input_tokens = tokenizer.tokenize(input_text)
        task, pin = parse_input_tokens(input_tokens)
    except:
        print('State not found, default is 0')
        state = 0

    if task is not None and pin is not None:
        board.digital[pin].write(task)
        time.sleep(1)
    else:
        print('Orden no reconocida')


if __name__ == "__main__":
    main(0)
