def distributive_and_over_or(a, b, c):
    return a and (b or c), (a and b) or (a and c)

def distributive_or_over_and(a, b, c):
    return a or (b and c), (a or b) and (a or c)

A = True
B = True
C = False

# Distributive Law: AND over OR
and_over_or_left, and_over_or_right = distributive_and_over_or(A, B, C)
print("Distributive Law - AND over OR")
print(f"A AND (B OR C)        = {and_over_or_left}")
print(f"(A AND B) OR (A AND C)= {and_over_or_right}")
print("Law holds:", and_over_or_left == and_over_or_right, "\n")

# Distributive Law: OR over AND 
or_over_and_left, or_over_and_right = distributive_or_over_and(A, B, C)
print("Distributive Law - OR over AND")
print(f"A OR (B AND C)        = {or_over_and_left}")
print(f"(A OR B) AND (A OR C) = {or_over_and_right}")
print("Law holds:", or_over_and_left == or_over_and_right, "\n")
