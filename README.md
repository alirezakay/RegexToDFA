# RegexToDFA

> This is how to convert regex (REGular EXpression) to DFA by creating and using syntax tree with ***Java*** code.<br>

This project is a part of a bigger project we've done on Term 5 of University (@ KNTU) for **Compiler Course** to creating a simple compiler. 

<hr>

## Getting Started

:small_blue_diamond: Watch this video to comprehend the **_concepts_**: https://www.youtube.com/watch?v=uPnpkWwO9hE<br><br>

Attention to some Rules:<br>

![rule3](https://github.com/alirezakay/RegexToDFA/blob/master/assets/img/rule2.png)
![rule2](https://github.com/alirezakay/RegexToDFA/blob/master/assets/img/rule3.PNG)



**NetBeans** is the IDE I've coded in. you can clone this project and import it in NetBeans, easily.<br>

The classes used, are as follows:
- RegexToDfa
- SyntaxTree
- BinaryTree
- Node
- LeafNode
- DfaTraversal
- State

here is an initialize method which is called in the main function:
```java
public static void initialize() {
    DStates = new HashSet<>();
    input = new HashSet<String>();

    String regex = getRegex();
    getSymbols(regex);

    SyntaxTree st = new SyntaxTree(regex);
    root = st.getRoot();
    followPos = st.getFollowPos();

    State q0 = createDFA();
    DfaTraversal dfat = new DfaTraversal(q0, input);    
}
```
- **DStates** is a _Set_ of States which is used for creating the final dfa.<br>

- **input** is also a _Set_ which holding the characters of the input regular expression taken from user.<br><br>

  pay attention to this issue that, some characters like '\*' can be used as an operator (closure, union, concatination, ...)<br>
  
  so if you want to enter these characters just as a normal character, you could bring a backslash '\' following up the intended character<br>
  
  for example "\\\*" meaning a normal '\*' character. and "\*" meaning star opeartor (closure)<br>
  this is why we use a set of _String_ for declaring input variable.<br><br>
  
- **`String regex = getRegex();`** is for getting the intended regular expression from stdin.<br>

- **`getSymbols(regex);`** this line of code sets the _input_ Set.<br>

- **`SyntaxTree st = new SyntaxTree(regex);`** and this line creates the corresponding syntax tree of the inputted regex.<br>

- **`root = st.getRoot();`** gets the root of the syntax tree.<br>

- **`followPos = st.getFollowPos();`** make a new refference to the _Set_ of followpos variable.<br>

- **`State q0 = createDFA();`** creates the DFA through using the syntax tree and assigns q0 the start state of resulted DFA.<br>

- **`DfaTraversal dfat = new DfaTraversal(q0, input);`** makes a new DFA Traversal object for traversing the resulted DFA and recognizing whether the DFA can accept a particular string or not.<br>

<hr>

### Proof Of Concepts

```java
/*
    ***
        (a|b)*a => creating binary syntax tree:
                            .
                           / \
                          *   a
                         /
                        |
                       / \
                      a   b
    ***
*/
```

if you look at the **SyntaxTree** class, you will understand that a **BinaryTree** object is created.

**_so a syntax tree is a binary tree with some new special attributes (firstpos, lastpos, nullable, followpos)_**.

in the BinaryTree class, the inputted regex is going to convert to a tree which contains some nodes.

the last most bottom nodes are called, the leaf nodes.

thus, now you comprehend what the **Node** and **LeafNode** are used for.

### Authors
- ALireza Kavian - phase 1 - ![BRILAC ASCK](https://www.github.com/BRILACASCK)
