/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pyramid_scheme;

import ADTs.TreeADT;
import Exceptions.ElementNotFoundException;
import java.util.ArrayList;

/**
 *
 * @author pmele (modified by ITSC 2214)
 * @param <T>
 * @version 3/28/2019
 */
public class LinkedTree<T extends Comparable<T>> implements TreeADT<T> {
    /**
     * The root of the tree.
     */
    protected MultiTreeNode<T> root;
    
    /**
     * Normal constructor 
     * Create the root node which holds the input data element 
     * as in the parameter variable startElem
     * 
     * @param startElem 
     */
    public LinkedTree(T startElem) {
        root = new MultiTreeNode<>(startElem);
    }
    
    /**
     * Normal constructor 
     * Use the input startNode as the root node 
     * @param startNode the root node of the tree
     */
    public LinkedTree(MultiTreeNode<T> startNode) {
        root = startNode;
    }
    
    /**
     * TODO Returns the element stored at the root
     * 
     * @return root element
     */
    @Override
    public T getRootElement() {
        return root.getElement();
    }
    
    /**
     * Retrieve root node
     * @return MultiTreeNode<T> root node
     */
    private MultiTreeNode<T> getRootNode() {
        return root;
    }
    
    /**
     * TODO Adds a node containing the child element to another 
     * node that is already in the tree containing the parent 
     * element. This particular overload of the addChild method 
     * is responsible for locating the parent node, then
     * calling the other overload to complete the task.
     * @param parent An element contained by a particular node in 
     * the tree, which will ultimately be given a child node
     * @param child The element to be packaged into a node and 
     * added to the tree 
     * @throws ElementNotFoundException 
     */
    public void addChild(T parent, T child) throws ElementNotFoundException {
        MultiTreeNode<T> parent1 = findNode(parent);
        if(child == null || parent == null)
        {
            return;
        }
        
        if(parent1 == null)
        {
            throw new ElementNotFoundException("Cannot find element.");
        }
        addChild(parent1, child);
        
    }
    
    /**
     * TODO Adds a node containing the child element to another 
     * node that is already in the tree containing the parent 
     * element. This particular overload of the addChild method 
     * is responsible for creating a node for child and
     * adding it to the children of parentNode.
     * @param parentNode the node receiving a child node
     * @param child the element to be packaged and added as a 
     * child node
     */
    protected void addChild(MultiTreeNode<T> parentNode, T child) {
        MultiTreeNode<T> new1 = new MultiTreeNode<T>(child);
        
        parentNode.addChild(new1);
        
        return;
    }
    
    /**
     * Finds the node that contains a target element. Calls the 
     * recursive overload to search for target.
     * @param target the element being searched for
     * @return the MultiTreeNode containing the target, or null 
     * if not found
     */
    public MultiTreeNode<T> findNode(T target) {
        if (target == null) {
            return null;
        }
        return findNode(root, target);
    }
    
    /**
     * Finds the node that contains a target element. This checks 
     * the current node, and if it is the target, returns it. 
     * Otherwise, it recursively checks each of the current node's
     * children, to see if they can find it.
     * If none of this node's children can find it either,
     * then null is returned.
     * @param node the node currently being examined
     * @param target the element being searched for
     * @return the MultiTreeNode containing the target, or null 
     * if not found
     */
    private MultiTreeNode<T> findNode(MultiTreeNode<T> node, T target) {
        //If this node is the one holding the target...
        if (node.getElement().compareTo(target) == 0) {
            return node; //...Return this node, so it is passed upwards.
        } 
        else {
            MultiTreeNode<T> temp; 
            for (MultiTreeNode<T> child : node.getChildren()) {
                temp = findNode(child, target); 
                if (temp != null) {
                    return temp;
                }
            }
            return null;
        }
    }

    /**
     * TODO 
     * Tries to find a node that contains the target element, 
     * and indicates true if it can, false if it cannot.
     * @param target the element being searched for.
     * @return a boolean representing whether or not the tree 
     * contains a node with the target element.
     */
    @Override
    public boolean contains(T target) {        
        if(findNode(root, target) == null)
        {
            return false;
        }
        else
        {
        return true;
        }
        
    }
    
    /**
     * Returns the size of the tree, measured by how many nodes 
     * are in it. Calls the recursive method countDown to 
     * determine this.
     * @return the size of the tree.
     */
    @Override
    public int size() {
        if (root == null) {
            return 0;
        }
        return countDown(root);
    }
    
    /**
     * TODO
     * Recursively counts the current node, its children, and so 
     * on. Used by size() to determine how many nodes are in the 
     * tree.
     * @param node the node currently being examined.
     * @return the amount of nodes from the starting node down.
     */
    private int countDown(MultiTreeNode<T> node) {
        if(node == null) {
            return 0;
        }
        
        if(node.getNumChildren() == 0) {
            return 1;
        }
        
        int num = 1;
        
        for (MultiTreeNode<T> node2 : node.getChildren())
        {
            num += countDown(node2);
        }
        
        
        return num;
    }
    
    /**
     * Retrieve a text representation of a tree rooted by given node
     * @return A series of lines demonstrating elements on each layer.
     */
    @Override
    public String toString() {
        if (this.root.getChildren() == null 
                || this.root.getChildren().size() == 0)
            return "";
        
        return toString(this.root);
        /*String print = "Linked Tree: \nLayer 1 (Root): " 
        + root.getElement();
        ArrayList<MultiTreeNode<T>> layer = root.getChildren();
        ArrayList<MultiTreeNode<T>> nextLayer;
        int layerNum = 2;
        while (!layer.isEmpty()) {
            print += "\nLayer " + layerNum + ":";
            nextLayer = new ArrayList<>();
            for (MultiTreeNode<T> node : layer) {
                nextLayer.addAll(node.getChildren());
                print += " " + node;
            }
            layer = nextLayer;
            layerNum++;
        }
        return print;*/
    }

    /**
     * Retrieve a text representation of a tree rooted by given node
     * @param node root node of a subtree
     * @return a text representation
     */
    public String toString(MultiTreeNode<T> node) {
        if (node.getChildren() == null 
            || node.getChildren().size() == 0)
            return node.getElement().toString();
        
        String treeinfo = node.getElement().toString() + "\n";
        for (MultiTreeNode<T> child : node.getChildren())
            treeinfo += child.toString() + "\n";
        
        return treeinfo;
    }
    
    /**
     * Count the number of leaf nodes in the tree
     * @return number of leaf nodes in the tree or subtree 
     *           rooted by given node
     */
    public int leafCounter() {
        return leafCounter(root);
    }
    
    /**
     * leafCounter
     * If the parameter node is empty, the tree rooted by the 
     * parameter node is empty, having zero leaf node.
     * If the parameter node has empty left subtree and empty 
     * right subtree, the parameter node is the only node in 
     * the tree rooted by the parameter node, and it is the 
     * unique leaf node. Or else, the tree or subtree rooted by 
     * the parameter node includes  more than one node. The number
     * of leaves of such a tree is the addition of the numbers of 
     * left subtree and right subtree rooted by the parameter node.
     * Write a recursive method to calculate the number of leaf nodes
     * in a tree rooted by the parameter node.
     * Note: Before writing the code, first analyze the problem
     * and answer the following questions:
     * - What is the recursive property?
     * - What is the base case?
     * - What is the recursive relation?
     * Note: when you design and write your code, please take care of
     * base case first
     * @param node 
     * @return   number of leaf nodes in the tree or subtree 
     *           rooted by given node
     */
    public int leafCounter(MultiTreeNode<T> node) {
        if (node == null) return 0;
        if (node.isLeaf())
            return 1;
        
        int leafs = 0;
        for (MultiTreeNode<T> child : node.getChildren())
            leafs += leafCounter(child);    
        return leafs;
    }
        

    /**
     * Examine whether the tree is empty
     * @return true - the tree is empty
     *         false - the tree is not empty
     */
    public boolean isEmpty() {
        return this.root == null;
    }
}
