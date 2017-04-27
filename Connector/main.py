from pyfirmata import Arduino, util, time
import sys

board = Arduino('/dev/ttyACM0')

def main(argv):
    # state = False
    # for i in range(100):
    #     board.digital[3].write(int(state))
    #     time.sleep(0.1)
    #     state = not state
    # board.digital[3].write(0)

    state = sys.argv[1]
    print('State: ' + str(state))
    board.digital[3].write(int(state))
    time.sleep(1)

if __name__ == "__main__":
    main(sys.argv[0:])

print ('Number of arguments:', len(sys.argv), 'arguments.')
print ('Argument List:', str(sys.argv))