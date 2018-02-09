/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regextodfa;

import java.util.Set;

/**
 *
 * @author @ALIREZA_KAY
 */
public class DfaTraversal {
    
    private final State q0;
    private State curr;
    private char c;
    private final Set<String> input;
    
    public DfaTraversal(State q0, Set<String> input){
        this.q0 = q0;
        this.curr = this.q0;
        this.input = input;
    }
    
    public boolean setCharacter(char c){
        if (!input.contains(c+"")){
            return false;
        }
        this.c = c;
        return true;
    }
    
    public boolean traverse(){
        curr = curr.getNextStateBySymbol(""+c);
        return curr.getIsAcceptable();
    }
}
