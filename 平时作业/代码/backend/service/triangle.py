class Triangle:
    def __init__(self,a,b,c):
        self.a=a
        self.b=b
        self.c=c
    def compute_triangle(self):
        if 1 <= self.a <= 10 and 1 <= self.b <= 10 and 1 <= self.c <= 10:
            if self.a + self.b > self.c and self.a + self.c > self.b and self.b + self.c > self.a:
                if self.a == self.b and self.a == self.c and self.b == self.c:
                    return "等边三角形"
                elif self.a == self.b or self.a == self.c or self.b == self.c:
                    return "等腰三角形"
                else:
                    return "三边均不等的三角形"
            else:
                return "不构成三角形"
        else:
            return "不构成三角形"

