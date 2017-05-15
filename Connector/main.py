from pyfirmata import Arduino, util, time
from bluetooth import *
import sys

board = Arduino('/dev/ttyACM0')


def main(argv):
    print("performing inquiry...")

    nearby_devices = discover_devices(
        duration=8, lookup_names=True, flush_cache=True, lookup_class=False)

    print("found %d devices" % len(nearby_devices))

    for addr, name in nearby_devices:
        try:
            print("  %s - %s" % (addr, name))
        except UnicodeEncodeError:
            print("  %s - %s" % (addr, name.encode('utf-8', 'replace')))

    server_sock = BluetoothSocket(RFCOMM)
    server_sock.bind(('', PORT_ANY))
    server_sock.listen(1)
    port = server_sock.getsockname()[1]

    try:
        state = sys.argv[1]
    except:
        print('State not found, default is 0')
        state = 0
    print('Current state: ' + str(state))

    board.digital[3].write(int(state))
    time.sleep(1)


if __name__ == "__main__":
    main(0)
