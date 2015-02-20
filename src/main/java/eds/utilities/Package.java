/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eds.utilities;

import java.util.Iterator;
import java.util.Stack;

/**
 * Produces a directory path of a Java Package. Its operations are emulated as 
 * a Stack. Example:
 * 
 * - Push "seca2"
 * - Push "package"
 * - Push "programs"
 * 
 * produces "seca2.package.programs"
 * 
 * @author vincent.a.lee
 */
public class Package {
    private Stack<String> stack = new Stack<String>();
    
    public Package push(String childPackage){
        stack.push(childPackage);
        return this;
    }
    
    public String pop(){
        return stack.pop();
    }

    public Stack<String> getStack() {
        return stack;
    }

    public void setStack(Stack<String> stack) {
        this.stack = stack;
    }
    
    @Override
    public String toString() {
        String output = "";
        Stack<String> copy = (Stack<String>) stack.clone();
        
        //Printing must be FIFO, not LIFO
        Iterator i = copy.iterator();
        boolean first = true;
        while(i.hasNext()){
            if(first){
                output += i.next();
                first = false;
            }
            else output += "."+i.next();
        }
        
        return output;
    }

    @Override
    protected Package clone() throws CloneNotSupportedException {
        Stack<String> copy = (Stack<String>) stack.clone();
        
        Package newPackage = new Package();
        newPackage.setStack(copy);
        return newPackage;
    }
    
    
}
