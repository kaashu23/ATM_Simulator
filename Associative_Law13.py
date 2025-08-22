def associative_and(a, b, c):
    return (a and b) and c, a and (b and c)

def associative_or(a, b, c):
    return (a or b) or c, a or (b or c)

# (True = fact is known, False = fact is not known)
A = True   # fact: "It is sunny"
B = True   # fact: "I have free time"
C = False  # fact: "I will go to the park"

# AND test (Inference: All facts must be true)
and_left, and_right = associative_and(A, B, C)
print("Associative Law - AND")
print(f"(A AND B) AND C = {and_left}")
print(f"A AND (B AND C) = {and_right}")
print("Law holds for AND:", and_left == and_right, "\n")

# OR Test (Inference: At least one fact is true)
or_left, or_right = associative_or(A, B, C)
print("Associative Law - OR")
print(f"(A OR B) OR C = {or_left}")
print(f"A OR (B OR C) = {or_right}")
print("Law holds for OR:", or_left == or_right, "\n")
