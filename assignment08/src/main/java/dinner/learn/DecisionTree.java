package dinner.learn;

import dinner.model.Decision;
import dinner.model.Example;
import dinner.model.Attribute;

import java.util.*;

public class DecisionTree {
	
    public DecisionTree(List<Example> examples, List<Attribute> attributes) {
        createDecisionTree(examples, attributes);
    }
    
    public static Attribute getMostImportantAttribute(List<Example> examples, List<Attribute> attributes) {
        Map<Object, Map<Decision, Integer>> decisionMap = new HashMap<Object, Map<Decision, Integer>>();
        double numDecided = 0;
        double important = Integer.MIN_VALUE;
        Attribute maxAttribute = attributes.get(0);
        for(int i = 0; i < attributes.size(); i++)
        {
            decisionMap = generateDecisionMap(examples, attributes.get(i));
            numDecided = getNumOutcomesDecided(decisionMap);
            if(important < numDecided)
            {
                important = numDecided;
                maxAttribute = attributes.get(i);
            }
        }
        return maxAttribute;
    }
  
    public static void createDecisionTree(List<Example> examples, List<Attribute> attributes) {
    	Map<Object, Map<Decision, Integer>> decisionMap = new HashMap<Object, Map<Decision, Integer>>();
    	ArrayList<Object> branches = new ArrayList<Object>();
    	Attribute mostImportantAttribute = null;
    	
    	mostImportantAttribute = getMostImportantAttribute(examples, attributes);
    	
    	boolean check;
    	for(int i = 0; i < examples.size(); i++)
    	{
    		check = true;
    		Object branch = examples.get(i).getAttributeValue(mostImportantAttribute);
    		
    		for(int j = 0; j < branches.size(); j++)
    		{
    			if(branch.equals(branches.get(j)))
        		{
        			check = false;
        		}
    		}
    		
    		if(check == true)
    		{
    			branches.add(branch);
    		}
    	}
    	Object[] dayOfWeekTest = decisionMap.keySet().toArray();
    	Arrays.sort(dayOfWeekTest);
    	Object[] dayOfWeek = new Object[branches.size()];
    	Collections.sort(attributes);
    	for(int i = 0; i < dayOfWeek.length; i++)
    	{
    		dayOfWeek[i] = branches.get(i);
    	}
    	Arrays.sort(dayOfWeek);
    	
    	System.out.println("Node: " + mostImportantAttribute);
    	System.out.println();
    	String par = mostImportantAttribute.toString();
    	String incBranch = branches.get(0).toString();
    	
    	for(int i = 0; i < branches.size(); i++)
    	{
    		createDecisionTree(par, incBranch, 0, attributes, examples, mostImportantAttribute, dayOfWeek[i]);
    	}    	
    }
    
    private static void createDecisionTree(String par, String incBranch, int iterate, List<Attribute> attributes, List<Example> examples, Attribute parent, Object branchInc)
    {
    	boolean decision = true;
    	boolean check;
		List<Attribute> updatedAttributes = new ArrayList<Attribute>();
		ArrayList<Example> updatedExamples = new ArrayList<Example>();
    	ArrayList<Object> incomingBranchValues = new ArrayList<Object>();
    	
    	for(int i = 0; i < examples.size(); i++)
    	{
    		for(int j = 0; j < attributes.size(); j++)
    		{
    			Object branch = null;
    			branch = examples.get(i).getAttributeValue(attributes.get(j));
    			if(attributes.get(j).equals(parent) && branch.equals(branchInc))
    			{
    				incomingBranchValues.add(examples.get(i).getDecision());
    			}
    		}
    	}
    	
//    	if(parent.equals("DAY"))
//    	{
//    		List<Example> examplesUpdate = new ArrayList<Example>();
//        	for(int i = 0; i < examples.size(); i++)
//        	{
//        		if(examples.get(i).getDay().equals(dayOfWeek[iterate]))
//        		{
//        			examplesUpdate.add(examples.get(i));
//        		}
//        		
//        	}
//        	Attribute importantAttribute = getMostImportantAttribute(examplesUpdate, attributes);
//        	iterate = iterate + 1;
//        	createDecisionTreehelper(decisionMap, parent, incomingBranch, dayOfWeek, iterate++, examples, attributes);
//        	
//        	// here we will run 2 for-loops for parent and incoming branch to determine if we're printing Decision or Node
//        	System.out.println("Parent: ");
//        	System.out.println("Branch: ");
//        	
//        	if()
//        	{
//        		System.out.println("Decision: ");
//        	}   		
//        	else
//        	{
//        		System.out.println("Node: ");
//        	}
//    	}
    	
    	for(int i = 0; i < attributes.size(); i++)
    	{
    		if(attributes.get(i).equals(parent) == false)
    		{
    			updatedAttributes.add(attributes.get(i));
    		}
    	}
    	
    	if(incomingBranchValues.size() > 0)
    	{
    		for(int i = 0; i < incomingBranchValues.size()-1; i++)
        	{
    			if(incomingBranchValues.get(i).equals(incomingBranchValues.get(i+1)) && decision == true)
    			{
    				decision = true;
    			}
    			else
    			{
    				decision = false;
    			}
        	}
    	}
    	
    	if(decision == true)
    	{
    		int numberDecided = incomingBranchValues.size();
    		String incomingBranchVal = incomingBranchValues.get(0).toString();
    		System.out.println("Parent: " + parent);
    		System.out.println("Branch: " + branchInc);
    		System.out.println("Decision (" + numberDecided + "): " + incomingBranchVal);
    		System.out.println();
    	}
    	else
    	{    		
    		for(int i = 0; i < examples.size(); i++)
        	{
        		for(int j = 0; j < attributes.size(); j++)
        		{        			
        			check = true;
        	    	for(int k = 0; k < updatedExamples.size(); k++)
        	    	{
        	    		if(examples.get(i).equals(updatedExamples.get(k)))
        	    		{
        	    			check = false;
        	    		}
        	    	}
        	    	
        	    	if(check && attributes.get(j).equals(parent) && examples.get(i).getAttributeValue(attributes.get(j)).equals(branchInc))
        	    	{
        	    		updatedExamples.add(examples.get(i));
        	    	}
        		}
        	}
    		
    		Attribute mostImportantNode = getMostImportantAttribute(updatedExamples, updatedAttributes);   		
    		System.out.println("Parent: " + parent);
    		System.out.println("Branch: " + branchInc);
    		System.out.println("Node: " + mostImportantNode);
    		System.out.println();
    		
    		par = parent.toString();
    		incBranch = branchInc.toString();
    		
    		ArrayList<Object> branches = new ArrayList<Object>();
    		for(int i = 0; i < updatedExamples.size(); i++)
        	{
    			check = true;
        		Object branch = updatedExamples.get(i).getAttributeValue(mostImportantNode);
        		
        		for(int j = 0; j < branches.size(); j++)
        		{
        			if(branch.equals(branches.get(j)))
            		{
            			check = false;
            		}
        		}
        		
        		if(check == true)
        		{
        			branches.add(branch);
        		}
        	}
    		
    		Object[] dayOfWeek = new Object[branches.size()];
        	for(int i = 0; i < dayOfWeek.length; i++)
        	{
        		dayOfWeek[i] = branches.get(i);
        	}
        	
        	Arrays.sort(dayOfWeek);
        	
        	for(int i = 0; i < branches.size(); i++)
        	{
        		createDecisionTree(par, incBranch, iterate++, updatedAttributes, updatedExamples, mostImportantNode, dayOfWeek[i]);
        	}
    	}
    }
 

    private static Map<Object, Map<Decision, Integer>> generateDecisionMap(List<Example> examples, Attribute attribute) {
        Map<Object, Map<Decision, Integer>> decisionMap = new HashMap<Object, Map<Decision, Integer>>();
        Map<Decision, Integer> decisionCountMap;
        Object attributeValue;
        Decision decision;

        for(Example example: examples) {
            attributeValue = example.getAttributeValue(attribute);
            decision = example.getDecision();
            if(decisionMap.containsKey(attributeValue)) {
                decisionCountMap = decisionMap.get(attributeValue);
                if(decisionCountMap.containsKey(decision)) {
                    decisionCountMap.put(decision, decisionCountMap.get(decision)+1);
                } else {
                    decisionCountMap.put(decision, 1);
                }
            } else {
                decisionCountMap = new HashMap<Decision, Integer>();
                decisionCountMap.put(decision, 1);
                decisionMap.put(attributeValue, decisionCountMap);
            }
        }
        return decisionMap;
    }

    private static double getNumOutcomesDecided(Map<Object, Map<Decision, Integer>> decisionMap) {
        int numDecided = 0;
        for (Object attrValue : decisionMap.keySet()) {
            if (decisionMap.get(attrValue).keySet().size() == 1) {
                numDecided++;
            }
        }
        return numDecided;
    }
}

