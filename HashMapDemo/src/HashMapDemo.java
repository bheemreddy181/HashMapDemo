import java.util.ArrayList;
import java.util.List;

public class HashMapDemo {

	/*
	 *  Container to hold key and value
	 */
	class Container{
		Object key;
		Object value;

		Container( Object k, Object v){
			this.key = k;
			this.value = v;
		}
	}

	/*
	 *  Result to hold intermediate result keyList
	 */
	class Result{
		Container container;
		List<Container> keyList;
		
		Result() {};
		
		Result(Container container, List<Container> keyList){
			this.container = container;
			this.keyList = keyList;
		}
	}
	private List<List<Container>> currentList;
	private int hashSize;
	static final int INITIAL_HASH_SIZE = 5;
	
	public HashMapDemo() {
		List<Container> objList;
		
		hashSize = INITIAL_HASH_SIZE;
		this.currentList = new ArrayList<>();

		// Initialize the List with given hashSize 
		for (int i = 0 ; i < hashSize; i++)
		{
			objList = new ArrayList<>();
			this.currentList.add(i, objList);
		}
	}

	private int getHashIndex(Object key)
	{
		int idx = 0;

		idx = key.hashCode() % hashSize;
		
		return idx;
	}
	
	/* Method to get the key, value and its keyList for a given key
	 * key input key
	 */
	private Result getContainer(Object key){
		int idx = 0;
		List<Container> objList;
		Result res = new Result();
		
		idx = getHashIndex(key);
		objList = currentList.get(idx);
		res.keyList = objList;
		
		if (objList != null) {
			for (int i = 0; i < objList.size(); i++){
				Container container = objList.get(i);
				if (container.key.equals(key)){
					res.container = container;
					break;
				}
			}
		}
		
		return res;
	}
	
	/* This method returns the value for a given key
	 * key given input key
	 * returns value on finding the key
	 * returns null otherwise 
	 */
	public Object get(Object key){
		
		Result res = getContainer(key);

		if( res.container != null ){
			return res.container.value;
		}

		return null;
	}

	/*
	 *  Method that removes an element from the hashMap
	 */
	public void remove(Object key){
		
		Result res = getContainer(key);
		Container container = res.container;
		List<Container> keyList = res.keyList;
		
		if( container != null && keyList != null){
			keyList.remove(container);
		}
	}

	/* Method to insert key and value to ArrayList
	 * k input key
	 * v input value
	 */
	public void put(Object key, Object value){

		List<Container> keyList = null;
		Result res = getContainer(key);
		
		Container container = null;
		if( res != null)
		 container = res.container;
		
		if( container != null){
			container.value = value;
		}else {
			
			keyList = res.keyList;
					
			if (keyList == null ){
				int idx = getHashIndex(key);
				keyList = new ArrayList<Container>();
				currentList.add(idx, keyList);
			}

			Container c = new Container(key, value);
			keyList.add(c);
		}
		
		// calculate load factor for every put function
		// re-Hash the ArrayList to accomodate new elements
		// during re-Hash need logic to maintain list of re-hashed elements
	}

	/* Main method that adds, reads and deletes elements to/from HashMap
	 * 
	 */
	public static void main(String[] args) {

		HashMapDemo hmd = new HashMapDemo();

		// insert values into hashmap
		hmd.put("100", "Hash");
		hmd.put("101", "Map");
		hmd.put("102", "Demo");

		// insert one more to the list
		hmd.put("103", "Success");

		// Read all the elements from the hashMap
		System.out.println("key: 100 value: "+ hmd.get("100"));
		System.out.println("key: 101 value: "+ hmd.get("101"));
		System.out.println("key: 102 value: "+ hmd.get("102"));

		// Read value not present in the hashMap should throw null
		System.out.println("Invalid Key: 105 value: "+ hmd.get("105"));
		
		// Remove an element from the hashMap
		hmd.remove("102");
		
		// Read value for a removed element
		System.out.println("Removed key: 102 value: "+ hmd.get("102"));
		
	}

}
