package RDRPOSTagger.jSCRDRtagger;

/**
 * @author DatQuocNguyen
 * 
 */

public class Node {
  public FWObject condition;
  public String conclusion;
  public Node exceptNode;
  public Node ifnotNode;
  public Node fatherNode;
  public int depth;

  public Node(FWObject inCondition, String inConclusion, Node inFatherNode, Node inExceptNode,
      Node inIfnotNode, int inDepth) {
    this.condition = inCondition;
    this.conclusion = inConclusion;
    this.fatherNode = inFatherNode;
    this.exceptNode = inExceptNode;
    this.ifnotNode = inIfnotNode;
    this.depth = inDepth;
  }

  public void setIfnotNode(Node node) {
    this.ifnotNode = node;
  }

  public void setExceptNode(Node node) {
    this.exceptNode = node;
  }

  public void setFatherNode(Node node) {
    this.fatherNode = node;
  }

  public int countNodes() {
    int count = 1;
    if (exceptNode != null) {
      count += exceptNode.countNodes();
    }
    if (ifnotNode != null) {
      count += ifnotNode.countNodes();
    }
    return count;
  }

  public boolean satisfy(FWObject object) {
    boolean check = true;
    for (int i = 0; i < 13; i++) {
      String key = condition.context[i];
      if (key != null) {
        if (!key.equals(object.context[i])) {
          check = false;
          break;
        }
      }
    }
    return check;
  }
}
