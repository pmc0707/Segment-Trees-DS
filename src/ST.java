public class ST {
    static int[] tree;
    public static void init(int num) {
        tree = new int[4*num];
    }
    public static void buildST(int [] arr, int idxST, int start, int end) {
        if(start == end) {
            tree[idxST] = arr[start];
            return;
        }
        int mid = (start + end)/2;
        buildST(arr,2*idxST+1,start,mid);//left subtree
        buildST(arr,2*idxST+2,mid+1,end); //right subtree
        tree[idxST] = tree[2*idxST+1] + tree[2*idxST+2];
    }
    public static int getSumUtil(int i ,int segmentI,int segmentJ,int queryI,int queryJ) {
    if(queryJ <= segmentI || queryI >= segmentJ) { //non overlapping
        return 0;
    } else if (segmentI >= queryI && segmentJ <= queryJ) { //complete overlapping
        return tree[i];
    }else { // partial overlapping
        int mid = (segmentI + segmentJ)/2;
        int left = getSumUtil(2*i+1,segmentI,mid,queryI,queryJ);
        int right = getSumUtil(2*i+2,mid+1,segmentJ,queryI,queryJ);
        return left + right;
    }
    }
    public static int getSum(int []arr ,int queryI,int queryJ){
        int n = arr.length;
        return getSumUtil(0,0,n-1,queryI,queryJ);
        }
    public static void updateUtil(int i,int segmentI,int segmentJ,int index,int diff) {
        if(index > segmentJ || index < segmentI) {
            return ;
        }
        tree[i] += diff;
        if(segmentI!=segmentJ) { //non leaf
            int mid = (segmentI + segmentJ)/2;
            updateUtil(2*i+1,segmentI,mid,index,diff);//Left
            updateUtil(2*i+2,mid+1,segmentJ,index,diff);//right
        }
    }
    public static void update(int []arr,int index,int newVal) {
        int n = arr.length;
        int diff = newVal - arr[index];
        arr[index] = newVal;

        updateUtil(0,0,n-1,index,diff);
    }

    public static void main(String[] args) {
        int [] numbers = {1,2,3,4,5,6,7,8};
        int num = numbers.length;
        init(num);
        buildST(numbers,0,0,num-1);

//        for (int i = 0; i < tree.length; i++) {
//            System.out.print(tree[i]+" ");
            for (int j : tree) { //enhance for loop
                System.out.print(j + " ");
        }
        System.out.println();
        System.out.println(getSum(numbers,2,5));
        update(numbers,2,2);
        System.out.println(getSum(numbers,2,5));
    }
}
