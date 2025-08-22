#Tower of Hanoi - move disk from one rod to another using recursion

def moveTower(height, fromPole, toPole, withPole):
    if height >= 1:
        moveTower(height- 1, fromPole, withPole, toPole)
        moveDisk(fromPole, toPole)
        moveTower(height - 1, withPole, toPole, fromPole)

def moveDisk(fp, tp):
    print("moving disk from", fp, "to", tp)
moveTower(3, "A", "B", "C")
