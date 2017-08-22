import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉排序树
 */
public class BinarySortTree<T extends Comparable<? super T>> {
    //根节点
    private Node root;

    public Node getRoot() {
        return root;
    }


    //二叉树节点
    class Node {
        T val;
        Node left;
        Node right;
        int count = 1;

        Node(T val) {
            this.val = val;
        }
    }

    /**
     * 层次遍历
     * 用一个链表保存节点,每次从前面取节点,同时把该节点的左右节点加入链表尾部
     *
     * @return 每个List表示一层元素
     */
    public List<List<T>> levelTraverse() {
        List<List<T>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        LinkedList<Node> list = new LinkedList<>();
        list.push(root);
        while (!list.isEmpty()) {
            //当前层元素数量
            int size = list.size();
            List<T> nowLevelElemts = new ArrayList<>();
            while (size-- > 0) {
                Node node = list.removeFirst();
                nowLevelElemts.add(node.val);
                if (node.left != null) {
                    list.addLast(node.left);
                }
                if (node.right != null) {
                    list.addLast(node.right);
                }
            }
            res.add(nowLevelElemts);
        }
        return res;
    }

    /**
     * 插入
     */
    public void insert(T val) {
        if (root == null) {
            root = new Node(val);
            return;
        }

        //找到的位置
        Node parentNode = root;
        Node curNode = root;
        while (curNode != null) {
            parentNode = curNode;
            if (val.compareTo(curNode.val) < 0) {
                curNode = curNode.left;
            } else if (val.compareTo(curNode.val) > 0) {
                curNode = curNode.right;
            } else {
                //已经存在val了
                curNode.count++;
                return;
            }
        }

        //插入新节点
        if (val.compareTo(parentNode.val) < 0) {
            parentNode.left = new Node(val);
        }
        if (val.compareTo(parentNode.val) > 0) {
            parentNode.right = new Node(val);
        }
    }

    /**
     * 查找指定值的父节点
     *
     * @return 找不到返回null, 找到则返回父节点
     */
    private Node findParent(Node root, T val) {
        if (root == null || val.equals(root.val)) {
            return null;
        }
        Node res = root;
        while (root != null) {
            if (root.val.equals(val)) {
                break;
            } else if (val.compareTo(root.val) > 0) {
                res = root;
                root = root.right;
            } else {
                res = root;
                root = root.left;
            }
        }
        return root == null ? null : res;
    }


    public boolean delNode(T val) {
        if (val.equals(root.val)) {
            delNode(root);
            return true;
        }
        //得到val的父节点
        Node parent = findParent(root, val);
        //val不存在
        if (parent == null) {
            return false;
        }
        return delNode(parent, parent.left.val.equals(val) ? parent.left : parent.right);
    }

    /**
     * @param parent 要删除节点的父节点
     * @param p      要删除的节点
     * @return 删除成功返回true, 否则返回false
     */
    private boolean delNode(Node parent, Node p) {
        if (parent == null) {
            throw new NullPointerException();
        }
        if (p == null) {
            return true;
        }
        if (parent.left != p && parent.right != p) {
            return false;
        }
        //p没有孩子节点,直接删除
        if (p.left == null && p.right == null) {
            if (parent.left == p) {
                parent.left = null;
            } else {
                parent.right = null;
            }

            //p只有一个孩子节点,让p的父节点指向p的孩子节点
        } else if (p.left == null || p.right == null) {
            if (parent.left == p) {
                parent.left = (p.left == null ? p.right : p.left);
            } else {
                parent.right = (p.left == null ? p.right : p.left);
            }
            //p有两个孩子节点时,找到p的左字树中最大的节点,p赋值为左字树中最大的节点的值,
            //同时删除左字树中最大的节点
        } else {
            parent = p;
            Node leftMaxNode = p.left;
            while (leftMaxNode.right != null) {
                parent = leftMaxNode;
                leftMaxNode = leftMaxNode.right;
            }
            p.val = leftMaxNode.val;
            //如果p的左孩子没有孩子节点,parent就是p,parent的左孩子就是左边最大的节点
            if (parent.left == leftMaxNode) {
                parent.left = leftMaxNode.left;
            } else {
                parent.right = leftMaxNode.left;
            }
        }
        return true;
    }

    /**
     * 寻找二叉树最大节点
     *
     * @param root
     * @return
     */
    public Node findMaxNode(Node root) {
        if (root == null) {
            return null;
        }
        //一直往右边走就对了
        Node res = root;
        while (res.right != null) {
            res = res.right;
        }
        return res;
    }

    /**
     * 寻找二叉树最小节点
     *
     * @param root
     * @return
     */
    public Node findMinNode(Node root) {
        if (root == null) {
            return null;
        }
        //一直往左边走就对了
        Node res = root;
        while (res.left != null) {
            res = res.left;
        }
        return res;
    }

    private boolean delNode(Node p) {
        if (p == null) {
            return true;
        }
        //要删除的节点为根节点
        if (p == root) {
            //找到左子树最大的节点
            if (p.left != null) {
                Node parent = p;
                Node leftMaxNode = p.left;
                while (leftMaxNode.right != null) {
                    parent = leftMaxNode;
                    leftMaxNode = leftMaxNode.right;
                }
                p.val = leftMaxNode.val;
                if (parent.left == leftMaxNode) {
                    parent.left = leftMaxNode.left;
                } else {
                    parent.right = leftMaxNode.left;
                }
                //找右子树最小的节点
            } else if (p.right != null) {
                Node parent = p;
                Node rightMinNode = p.right;
                while (rightMinNode.left != null) {
                    parent = rightMinNode;
                    rightMinNode = rightMinNode.left;
                }
                p.val = rightMinNode.val;
                if (parent.right == rightMinNode) {
                    parent.right = rightMinNode.right;
                } else {
                    parent.left = rightMinNode.right;
                }
                //都找不到,意味着二叉树只有一个节点
            } else {
                this.root = null;
            }
            return true;
        }
        Node pParent = findParent(root, p.val);
        //不存在p的父节点,那也不存在节点p
        if (pParent == null) {
            return false;
        }
        return delNode(pParent, p);
    }
}
