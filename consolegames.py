"""
Console games is a simple program that allows you to play simple games in your console. It is high quality using the curses library.
"""
import os
import termcolor
import random
from time import sleep
import curses
import sys
import enchant
# define the menu function

def finmen(status,playhandler,answ=None):
    if answ is not None:
        if status:
            m = menu(f"You win! The answer was {answ}. What would you like to do?",["Return to menu","Play again"],"blue")
            if m == 1:
                playhandler()
            else:
                return 0
        if not status:
            m = menu(f"You lose :(. The answer was {answ}. What would you like to do?",["Return to menu","Play again"],"blue")
            if m == 1:
                playhandler()
            else:
                return 0
    else:
        if status:
            m = menu("You win! What would you like to do?",["Return to menu","Play again"],"blue")
            if m == 1:
                playhandler()
            else:
                return 0
        elif status == None:
            m = menu("Stalemate. What would you like to do?",["Return to menu","Play again"],"blue")
            if m == 1:
                playhandler()
            else:
                return 0

        elif not status:
            m = menu("You lose :( What would you like to do?",["Return to menu","Play again"],"blue")
            if m == 1:
                playhandler()
            else:
                return 0
        
def guess_the_number():
    print("Loading...")
    mynum = random.randint(1,100)
    guesses = 10
    g = []
    won = False
    gameon = True
    while gameon:
        os.system("cls")
        screen = "Guess The Number\n\n" + "Guessed: " + ", ".join(g) + "\n" + "Guesses: " + str(guesses)
        print(screen)
        guess = input("Guessed Number: ")
        try:
            guess = int(guess)
        except:
            print("Please provide numbers only.")
            sleep(0.5)
        else:
            if str(guess) not in g:
                g.append(str(guess))
                if guess == mynum:
                    won = True
                    gameon = False
                else:
                    guesses -= 1
                    if guesses == -1:
                        gameon = False
                    if guess < mynum:
                        print("You guessed too low.")
                        sleep(0.5)
                    elif guess > mynum:
                        print("You guessed too high.")
                        sleep(0.5)

            else:
                print("You have already guessed this.")
                sleep(0.5)
    finmen(won,guess_the_number,mynum)


def hangman():
    #Hangman
    print("Loading hangman...")
    stages = [
    "\n"*6,
    """
    ()




    """,
    """
       ( )
        |



    """,
     """
        ( )
        /|



    """,
    """
        ( )
        /|\\


        
    """,
     """
        ( )
        /|\\
       / | \\

        
    """,
    """
        ( )
        /|\\
       / | \\
        / 
       / 
    """,
    """
        ( )
        /|\\
       / | \\
        / \\
       /   \\
    """
    ]
    
    gameon = True
    won = False
    stage = 0
    guesses = 8
    gwl = []

    os.system("cls")
    word = input("What word? ")
    if len(word) < 1 or "_" in word:
        print("Invalid character!")
        sleep(1)
    else:
        displayword = ["_"for i in range(len(word))]
        while gameon:
            chg = 0
            os.system("cls")
            screen = stages[stage] + "\n" + " ".join(displayword) +"\n" + "Guessed: "+", ".join(gwl) + "\n" + "Guesses left: " + str(guesses-1)
            print(screen)
            guess = input("What letter or word do you guess? ")
            if guess == "":
                print("Please provide a word.")
                sleep(0.5)
            else:
                if not guess in gwl:
                    gwl.append(guess)
                    if len(guess) > 1:
                        if guess == word:
                            won = True
                            gameon = False
                    
                    indexl = [pos for pos, char in enumerate(word) if char == guess]
                    if indexl:
                        for item in indexl:
                            chg += 1
                            displayword[item] = word[item]
                    if chg == 0:
                        stage += 1
                        guesses -= 1
                        if guesses == 0:
                            gameon = False
                    if not "_" in displayword:
                        won = True
                        gameon = False
                else:
                    print("You have already guessed this word!")
                    sleep(0.5)
                
        if won:
            m = menu(f"You win! The word was {word}. What would you like to do?",["return to menu","play again"],"blue")
        if not won:
            m = menu(f"You lose :(. The word was {word}. What would you like to do?",["return to menu","play again"],"blue")
        if m == 1:
            hangman() #oopsie... recursion
                    
    

def menu(title, classes, color='white'):
    # define the curses wrapper
    def character(stdscr,):
        attributes = {}
        # stuff i copied from the internet that i'll put in the right format later
        icol = {
        1:'red',
        2:'green',
        3:'yellow',
        4:'blue',
        5:'magenta',
        6:'cyan',
        7:'white'
        }
        # put the stuff in the right format
        col = {v: k for k, v in icol.items()}

        # declare the background color

        bc = curses.COLOR_BLACK

        # make the 'normal' format
        curses.init_pair(1, 7, bc)
        attributes['normal'] = curses.color_pair(1)


        # make the 'highlighted' format
        curses.init_pair(2, col[color], curses.COLOR_WHITE)
        attributes['highlighted'] = curses.color_pair(2)


        # handle the menu
        c = 0
        option = 0
        while c != 10:

            stdscr.erase() # cls the screen (you can erase this if you want)

            # add the title
            stdscr.addstr(f"{title}\n", curses.color_pair(1))

            # add the options
            for i in range(len(classes)):
                # handle the colors
                if i == option:
                    attr = attributes['highlighted']
                else:
                    attr = attributes['normal']
                
                # actually add the options

                stdscr.addstr(f'> ', attr)
                stdscr.addstr(f'{classes[i]}' + '\n', attr)
            c = stdscr.getch()

            # handle the arrow keys
            if c == curses.KEY_UP and option > 0:
                option -= 1
            elif c == curses.KEY_DOWN and option < len(classes) - 1:
                option += 1
        return option
    return curses.wrapper(character) #This code from stack overflow because i am too stupid

def wordle():
    wlist = ["a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"]
    corword = False
    ttry = 1
    d = enchant.Dict("en_US")
    
    while not corword:
        tc = False
        print(f"Getting word try {ttry}.",end="\r")
        word = [random.choice(wlist) for i in range(5)]
        for char in word:
            if word.count(char) > 1:
                tc = True
        if not tc:  
            if d.check("".join(word)):
                corword = True
            else:
                ttry += 1
    
    
    gameon = True
    won = False
    guesses = []
    nowords = []
    cwords = []
    iwords = []
    while gameon:
        os.system("cls")

        #Checking if guesses
        print(f"Got word in {ttry} tries.")

        #print("".join(word)) #For debug purposes
        if not guesses:
            print(6*("_ "*5+"\n"))
        else:
            
            for wrd in guesses:
                wrd = list(wrd)
                #Word handling and checking
                it = 0
                cwll = []
                for char in wrd:
                    cl = "white"
                    if char in cwll:
                        pass
                    else:
                        if wrd[it] == word[it]:
                            cl = "green" #For passing onto termcolor later
                            if not char in cwords:
                                cwords.append(char)
                        elif char in word:
                            cl = "yellow"
                            if not char in iwords:
                                iwords.append(char)
                        else:
                            cl = "white"
                            
                            if not char in nowords:
                                nowords.append(char)
                        cwll.append(char)
                    termcolor.cprint(char,cl,end=" ")
                    it += 1
                if wrd == word:
                    won = True
                    break
                print("\n",end="")
            if won:
                break
            print((6-len(guesses))*("_ "*5+"\n"))
        print("Letters not in word: " + ", ".join(nowords))
        print("Letters in word: " + ", ".join(cwords))
        iwords.clear()
        if len(guesses) == 6:
            break
        guess = input("Guess: ")
        if len(guess) > 0:
            if d.check(guess):
                if len(guess) == 5:
                    guesses.append(guess)
                else:
                    print("Insufficient length!")
                    sleep(0.5)
            else:
                print("Not a word!")
                sleep(0.5)


    finmen(won,wordle,"".join(word))
def cwordle():
    td = input("Length of word: ")
    try:
        td = int(td)
        if td < 2:
            raise ValueError("No")
    except:
        return -1 #Sorry Ive been programming in C too much
    lc = input("Guesses that user should get: ")
    try:
        lc = int(lc)
        if lc < 2:
            raise ValueError("no")
    except:
        return -1
    wlist = ["a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"]
    corword = False
    ttry = 1
    d = enchant.Dict("en_US")
    
    while not corword:
        tc = False
        print(f"Getting word try {ttry}.",end="\r")
        word = [random.choice(wlist) for i in range(td)]
        for char in word:
            if word.count(char) > 1:
                tc = True
        if not tc:  
            if d.check("".join(word)):
                corword = True
            else:
                ttry += 1
    
    
    gameon = True
    won = False
    guesses = []
    nowords = []
    cwords = []
    iwords = []
    while gameon:
        os.system("cls")

        #Checking if guesses
        print(f"Got word in {ttry} tries.")

        #print("".join(word)) #For debug purposes
        if not guesses:
            print(lc*("_ "*td+"\n"))
        else:
            
            for wrd in guesses:
                wrd = list(wrd)
                #Word handling and checking
                it = 0
                cwll = []
                for char in wrd:
                    cl = "white"
                    if char in cwll:
                        pass
                    else:
                        if wrd[it] == word[it]:
                            cl = "green" #For passing onto termcolor later
                            if not char in cwords:
                                cwords.append(char)
                        elif char in word:
                            cl = "yellow"
                            if not char in iwords:
                                iwords.append(char)
                        else:
                            cl = "white"
                            
                            if not char in nowords:
                                nowords.append(char)
                        cwll.append(char)
                    termcolor.cprint(char,cl,end=" ")
                    it += 1
                if wrd == word:
                    won = True
                    break
                print("\n",end="")
            if won:
                break
            print((lc-len(guesses))*("_ "*td+"\n"))
        print("Letters not in word: " + ", ".join(nowords))
        print("Letters in word: " + ", ".join(cwords))
        iwords.clear()
        if len(guesses) == lc:
            break
        guess = input("Guess: ")
        if len(guess) > 0:
            if d.check(guess):
                if len(guess) == td:
                    guesses.append(guess)
                else:
                    print("Insufficient length!")
                    sleep(0.5)
            else:
                print("Not a word!")
                sleep(0.5)


    finmen(won,cwordle,"".join(word))

def ttt():
    board = [" "*2 for i in range(9)]#Easy mode so I don't have to code an AI
    won = False
    gameon = True
    while gameon:
        olxm = False
        while not olxm:
            os.system("cls")
            screen = "Tic Tac Toe Singleplayer. You go first.\n"
            screen += f"+--+--+--+\n|{board[0]}|{board[1]}|{board[2]}|\n+--+--+--+\n|{board[3]}|{board[4]}|{board[5]}|\n+--+--+--+\n|{board[6]}|{board[7]}|{board[8]}|\n+--+--+--+"
            slm = menu(screen,[f"Put X on {i+1}" for i in range(9)],"blue")
            for i in range(9):
                if slm == i:
                    if board[i] == "  ":
                        board[i] = " X"
                        olxm = True
                    else:
                        print("There already is something here")
                        sleep(0.5)

        if (board[0] == " X" and board[1] == " X" and board[2] == " X") or (board[0] == " X" and board[3] == " X" and board[6] == " X") or (board[0] == " X" and board[4] == " X" and board[8] == " X"):
            won = True
            break
        if (board[2] == " X" and board[5] == " X" and board[8] == " X") or (board[2] == " X" and board[4] == " X" and board[6] == " X") or (board[8] == " X" and board[7] == " X" and board[6] == " X"):
            won = True
            break
        if (board[1] == " X" and board[4] == " X" and board[7] == " X") or (board[3] == " X" and board[4] == " X" and board[5] == " X"):
            won = True
            break
        wlist = []
        it = 0
        for space in board:
            if space == "  ":
                wlist.append(it)
            it += 1
        if not wlist:
            won = None
            break
        board[random.choice(wlist)] = " O"
        if (board[0] == " O" and board[1] == " O" and board[2] == " O") or (board[0] == " O" and board[3] == " O" and board[6] == " O") or (board[0] == " O" and board[4] == " O" and board[8] == " O"):
            
            break
        if (board[2] == " O" and board[5] == " O" and board[8] == " O") or (board[2] == " O" and board[4] == " O" and board[6] == " O") or (board[8] == " O" and board[7] == " O" and board[6] == " O"):
            
            break
        if (board[1] == " O" and board[4] == " O" and board[7] == " O") or (board[3] == " O" and board[4] == " O" and board[5] == " O"):
            
            break
        
    finmen(won,ttt)
#Menu
while True:
    os.system("cls")
    sysmen1 = menu("Console Games Main Menu | (c) 2022 Enderbyte Programs | v0.4.0",["Quit","Hangman","Guess The Number","Wordle","Custom Wordle","Tic Tac Toe (Easy)"],"blue")
    if sysmen1 == 0:
        
        sys.exit()
    elif sysmen1 == 1:
        hangman()
    elif sysmen1 == 2:
        guess_the_number()
    elif sysmen1 == 3:
        wordle()
    elif sysmen1 == 4:
        cwordle()
    elif sysmen1 == 5:
        ttt()
