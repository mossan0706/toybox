
from ast import Dict

START = 1
END = 100

word: Dict = {3: "Fizz", 5: "Buzz", 7: "Foo"}
ans: Dict = {}

for n in range(START, END + 1):
    ans[n] = ""

for key_div in word:
    for n in range(START, END + 1):
        if n % key_div == 0:
            ans[n] = ans[n] + word[key_div]
        else:
            ans[n] = ans[n] + ""


for key, item in ans.items():
    print(key, item)

# javaの奴とほぼ同じ形のものを記述
# まぁ可読性はあるけどPythonである意味はないので三項演算子のものも書いておく