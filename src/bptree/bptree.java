package bptree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;
import java.util.HashMap;

public class bptree {

	
	
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		int n = 0, k, v;
		String file;
		Bptree tree;
		String name;
		
		//커맨드모드 실행
		if(args.length == 0) {
			
			while(n<3) {
				System.out.println("Input new tree's degree: ");
				n = keyboard.nextInt();
				if(n<3)
					System.out.println("degree는 3이상 이어야 합니다");
			}
			tree = new Bptree(n);
			while(true) {
				System.out.println("//////////B plus tree//////////");
				System.out.println("1. insert \n2. delete \n3. print \n4. single_search \n5. range_search \n6. save \n7. road \n8. exit \n9. input file \n10. delete file");
				n = keyboard.nextInt();
				switch (n){
				case 1:
					System.out.println("key: ");
					k = keyboard.nextInt();
					System.out.println("value: ");
					v = keyboard.nextInt();
					tree.insert(k, v);
					break;
				case 2:
					System.out.println("key: ");
					k = keyboard.nextInt();
					tree.delete(k);
					break;
				case 3:
					System.out.println(tree);
					break;
				case 4:
					System.out.println("key: ");
					k = keyboard.nextInt();
					tree.single_search(k);
					break;
				case 5:
					System.out.println("key1: ");
					k = keyboard.nextInt();
					System.out.println("key2: ");
					v = keyboard.nextInt();
					tree.range_search(k,v);
					break;
				case 6:
					System.out.println("file name: ");
					file = keyboard.next();
					save(file,tree);
					break;
				case 7:
					System.out.println("file name: ");
					file = keyboard.next();
					tree = load(file);
					break;
				case 8:
					return;
				case 9:

					name = keyboard.nextLine();
					System.out.println("input file name :");
					name = keyboard.nextLine();
					try {
			            File csv = new File(".\\" + name);
			            BufferedReader br = new BufferedReader(new FileReader(csv));
			            String line = "";
			 
			            while ((line = br.readLine()) != null) {
			                // -1 옵션은 마지막 "," 이후 빈 공백도 읽기 위한 옵션
			                String[] token = line.split(",", -1);		                
			                tree.insert(Integer.parseInt(token[0]), Integer.parseInt(token[1]));
			            }
			            br.close();
			        } 
			        catch (FileNotFoundException e) {
			            e.printStackTrace();
			        } 
			        catch (IOException e) {
			            e.printStackTrace();
			        }
					break;
				case 10:
					name = keyboard.nextLine();
					System.out.println("input file name :");
					name = keyboard.nextLine();
					try {
						File csv = new File(".\\" + name);
			            BufferedReader br = new BufferedReader(new FileReader(csv));
			            String line = "";
			 
			            while ((line = br.readLine()) != null) {
			                // -1 옵션은 마지막 "," 이후 빈 공백도 읽기 위한 옵션
			                String[] token = line.split(",", -1);		                
			                tree.delete(Integer.parseInt(token[0]));
			            }
			            br.close();
			        } 
			        catch (FileNotFoundException e) {
			            e.printStackTrace();
			        } 
			        catch (IOException e) {
			            e.printStackTrace();
			        }
					break;
				}
			}
		//command-line interface
		} else {
			switch(args[0]) {
			case "-c" :
				if(Integer.parseInt(args[2])<3) {
					System.out.println("degree는 3이상 이어야 합니다");
					return;
				}
				save(args[1],new Bptree(Integer.parseInt(args[2])));
				break;
			case "-i" :
				tree = load(args[1]);
				try {
		            File csv = new File(".\\" + args[2]);
		            BufferedReader br = new BufferedReader(new FileReader(csv));
		            String line = "";
		            while ((line = br.readLine()) != null) {
		                // -1 옵션은 마지막 "," 이후 빈 공백도 읽기 위한 옵션
		                String[] token = line.split(",", -1);		                
		                tree.insert(Integer.parseInt(token[0]), Integer.parseInt(token[1]));
		            }
		            br.close();
		        } 
		        catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } 
		        catch (IOException e) {
		            e.printStackTrace();
		        }
		        save(args[1],tree);
				break;
			case "-d" :
				tree = load(args[1]);
				try {
		            File csv = new File(".\\" + args[2]);
		            BufferedReader br = new BufferedReader(new FileReader(csv));
		            String line = "";
		            while ((line = br.readLine()) != null) {
		                // -1 옵션은 마지막 "," 이후 빈 공백도 읽기 위한 옵션
		                String[] token = line.split(",", -1);		                
		                tree.delete(Integer.parseInt(token[0]));
		            }
		            br.close();
		        } 
		        catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } 
		        catch (IOException e) {
		            e.printStackTrace();
		        }
		        save(args[1],tree);
				break;
			case "-s" :
				tree = load(args[1]);
				tree.single_search(Integer.parseInt(args[2]));
				break;
			case "-r" :
				tree = load(args[1]);
				tree.range_search(Integer.parseInt(args[2]),Integer.parseInt(args[3]));
				break;
				
		}
	}
		
		
		

		
		
		
		
	}
	static void save(String file, Bptree tree) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            DataOutputStream oos = new DataOutputStream(fos);

            oos.writeInt(tree.n);
            oos.writeInt(tree.get_node_number());
            
			Node navi = tree.root;
			Node node = tree.root;
			while(true) {
				navi = node;
				while(true) {
					oos.writeInt(navi.id);
					oos.writeInt(navi.m);
					oos.writeBoolean(navi.isLeaf);
					if(navi.next == null)
						break;
					navi = navi.next;
				}
				if(node.isLeaf)
					break;
				node = (Node)node.p[0].value;
			}
			
			navi = tree.root;
			node = tree.root;
			while(true) {
				navi = node;
				while(true) {
					//속성 추가할 노드의 id 적기
					oos.writeInt(navi.id);
					//p 적기
					if(navi.isLeaf)
					{
						for(int i = 0; i < navi.get_p_num(); i++) {
							oos.writeInt(navi.p[i].key);
							oos.writeInt((int)navi.p[i].value);
						}
						
					} else
					{
						for(int i = 0; i < navi.get_p_num(); i++) {
						oos.writeInt(navi.p[i].key);
						oos.writeInt(((Node)navi.p[i].value).id);
					}
						
					}
					//r의 hash_key 적기
					if(navi.r != null)
						oos.writeInt(navi.r.id);
					else
						oos.writeInt(-1);

					//parent의 hash_key 적기
					if(navi.parent != null)
						oos.writeInt(navi.parent.id);
					else
						oos.writeInt(-1);

					//next의 hash_key 적기
					if(navi.next != null)
						oos.writeInt(navi.next.id);
					else
						oos.writeInt(-1);

					//pre의 hash_key 적기
					if(navi.pre != null)
						oos.writeInt(navi.pre.id);
					else
						oos.writeInt(-1);
					
					if(navi.next == null)
						break;
					navi = navi.next;
				}
				if(node.isLeaf)
					break;
				node = (Node)node.p[0].value;
			}
			oos.writeInt(tree.root.id);
			oos.writeInt(tree.id);

			oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	static Bptree load(String file) {
		Bptree result = null;
		HashMap<Integer, Node> index = new HashMap<Integer, Node>();
        try {
            FileInputStream fis = new FileInputStream(file);
            DataInputStream ois = new DataInputStream(fis);
            //노드안의 p의 최대크기 n과 트리의 노드갯수 number_of_node 읽기
            int n = ois.readInt();
            int number_of_node = ois.readInt();
            
            Node node;
            int id, m, r, parent, next, pre, key, value;
            boolean isLeaf;
            //tree 생성.
            result = new Bptree(n+1);
            for(int i = 0; i<number_of_node; i++) {
            	//id, m, isLeaf 값 읽기
            	id = ois.readInt();
            	m = ois.readInt();
            	
            	isLeaf = ois.readBoolean();
            	//hashMap에 추가하기
            	index.put(id, new Node(n,isLeaf));
            	//속성 읽을 node id로 설정하기
            	node = (Node)index.get(id);
            	//node의 m 속성 읽기
            	node.m = m;
            	//node의 id 속성 읽기
            	node.id = id;
            }
            for(int i = 0; i<number_of_node; i++) {
            	//속성 읽을 id 읽기
            	id = ois.readInt();
            	//속성 읽을 node id로 설정하기
            	node = (Node)index.get(id);
            	//node의 p속성 읽기
            	if(node.isLeaf) {
            		for(int j = 0; j < node.get_p_num(); j++) {
                    	key = ois.readInt();
            			value = ois.readInt();
            			node.p[j] = new Pair(key,value);
            		}
            	} else {
            		for(int j = 0; j < node.get_p_num(); j++) {
                    	key = ois.readInt();
            			value = ois.readInt();
            			node.p[j] = new Pair(key,(Node)index.get(value));
            		}
            	}
            	//node의 r속성 읽기
            	r = ois.readInt();
            	if(r != -1)
            		node.r = (Node)index.get(r);
            	//node의 parent속성 읽기
            	parent = ois.readInt();
            	if(parent != -1)
            		node.parent = (Node)index.get(parent);
            	//node의 next속성 읽기
            	next = ois.readInt();
            	if(next != -1)
            		node.next = (Node)index.get(next);
            	//node의 pre속성 읽기
            	pre = ois.readInt();
            	if(pre != -1)
            		node.pre = (Node)index.get(pre);            	
            }
            
            int root_id = ois.readInt();
        	result.root = (Node)index.get(root_id);
        	result.id = ois.readInt();
            ois.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

        return result;
    }

	public static class Bptree {
		int n;		// maximum # of keys of each node
		Node root;		// root Node;
		int id = 0; //각 노드들의 id 생성기;

		public Bptree(int n) {
			this.n = n-1;
			this.root = new Node(n, true);
			this.root.id = this.id++;
		}
		
		void insert(int key, int value) {
			Node node = root;
			int index;
			
			//해당 key가 들어있는 leaf노드 접근하기
			while(node.isLeaf == false) {
				index = node.search_path(key);
			
				if(index < node.get_p_num()) {
					node = (Node)node.p[index].value;
				} else {
					node = node.r;
				}
			}
			
			node.add(key, value);		
			if(node.get_p_num() > n) {
				split(node);
			}
		}
		
		void split(Node node) {
			
			Node node2;
			Node parent;

			if(node == this.root) {
				node2 = node.split();
				node2.id = this.id++;
				
				parent = new Node(this.n, false);
				parent.id = this.id++;
				this.root = parent;
				
				parent.add(node2.p[0].key,node);
				
				
				parent.r = node2;
				
				node.parent = parent;
				node2.parent = parent;
				
				//non leaf 노드일 때는 split 후 새로생긴 노드의 맨 앞 pair가 지워져야한다.
				if(node2.isLeaf == false)
					node2.remove(node2.p[0].key);
				/////////////////////////////////////////////////////
			} else {
				node2 = node.split();
				node2.id = this.id++;
				
				parent = node.parent;
				
				int index = parent.search_path(node2.p[0].key);
				if(index+1 > parent.get_p_num()) {								//부모노드에서 맨 오른쪽에 추가될 때
					index = parent.add(node2.p[0].key, node);
					parent.r = node2;
				} else {												//부모노드에서 맨 오른쪽이 아닐 때 
					index = parent.add(node2.p[0].key, node);
					parent.p[index+1].value = node2;
				}
				
				//non leaf 노드일 때는 split 후 새로생긴 노드의 맨 앞 pair가 지워져야한다.
				if(node2.isLeaf == false)
					node2.remove(node2.p[0].key);
				/////////////////////////////////////////////////////
				
				if(parent.get_p_num() > n) {
					split(parent);
				}
			}
		}
		
		void delete(int key) {
			Node node = root;
			int index;
			
			while(node.isLeaf == false) {
				index = node.search_path(key);
			
				if(index < node.get_p_num()) {
					node = (Node)node.p[index].value;
				} else {
					node = node.r;
				}
			}

			//찾아 간 node가 root라면 그냥 remove 한다.
			if(this.root == node) {		
				node.remove(key);
				return;
			}
		
			
			//해당 key 삭제를 시도하고 성공하면 필요한 상황일 때 대표 키값 교체, 실패하면 그냥 리턴
			if((index = node.remove(key)) >= 0) {		
				//삭제된 것이 leaf의 가장 왼쪽 값이라면
				if(index == 0) {
					//지우고 난 후 leaf에 element가 남아있으면 남은 element의 가장 왼쪽 값이 대표 키값이 된다.
					if(node.get_p_num() > 0)
						exchange_nonLeaf(key, node.p[0].key);
					//지우고 난 후 leaf에 element가 남아있지 않으면,
					else
					{
						//만약 제일 왼쪽에 달려 있다면
						if(node.parent.p[0].value == node) {
							exchange_nonLeaf(key, node.next.p[0].key);
						}
						//제일 왼쪽에 달려 있지 않으면 지울 필요가 없다.(merge_leaf 할 때 지워주기 때문에)
					}		
				}
			} 
			//지우려는 key가 존재하지 않을 경우 에러메세지 출력
			else {										
				System.out.println("Error: 해당 key가 존재하지 않음");
				return;
			}
			//만약 m이 너무 작아지면 merge 또는 borrow를 시작한다.
			if(node.get_p_num() < half(n)) {
				//현재 node(leaf)가 parent에 가장 오른쪽에 달려있으면 왼쪽과 합치던가 왼쪽에게 빌리고
				if(node.parent.r == node) {
					if(node.pre.get_p_num() + node.get_p_num()<=n) {
						merge_leaf(node.pre, node);
					}else {
						borrow_leaf(node,0);
					}
				//현재 node(leaf)가 parent에 가장 오른쪽이 아니라면 오른쪽과 합치던가 오른쪽에게 빌린다.
				} else {
					if(node.get_p_num() + node.next.get_p_num()<=n) {
						merge_leaf(node, node.next);
					}else {
						borrow_leaf(node,1);
					}
				}
			}
		}
	
			
		//n을 2로 나눠서 올림한 값을 리턴
		int half(int n) {
			int result;
			if(n%2 == 0){
				result = n/2;
			} else {
				result = n/2 + 1;
			}
			return result;
		}
		
		//non Leaf 노드들 중에서 toChange인 key값을 instead로 바꾼다.
		void exchange_nonLeaf(int toChange, int instead) {
			Node navi = root;
			int index;		
			while(navi.isLeaf == false) {
				
				for(index = navi.get_p_num()-1; index>=0; index--) {
					if(navi.p[index].key == toChange) {
						navi.p[index].key = instead;
						return;
					}
				}
				
				
				index = navi.search_path(toChange);
				if(index == navi.get_p_num())
					navi = navi.r;
				else
					navi = (Node)navi.p[index].value;
			}
		}
		

		void merge_leaf(Node node1, Node node2) {
			Node newNode = new Node(n,true);
			newNode.id = node1.id;
			int i, j, index;
			//node1의 값들 모두 넣기
			for(i = 0; i<node1.get_p_num(); i++) {
				newNode.p[i] = node1.p[i];
				newNode.m++;
			}
			//node2의 값을 모두 넣기
			for(j = newNode.get_p_num(), i = 0; i<node2.get_p_num(); i++, j++) {
				newNode.p[j] = node2.p[i];
				newNode.m++;
			}
			newNode.r = node2.r;
			//newNode의 기타 값들 설정하기(parent,pre,next)
			newNode.pre = node1.pre;
			newNode.next = node2.next;
			newNode.parent = node1.parent;
			
			//newNode를 주변 node들과 연결하기
			if(newNode.pre != null)
				newNode.pre.next = newNode;
			if(newNode.next != null)
				newNode.next.pre = newNode;
			
			index = newNode.parent.search_path(node1);	//node1으로 향하는 index 찾고
			if((index = newNode.parent.remove(newNode.parent.p[index].key))==newNode.parent.get_p_num()) // 지우고나서 그 index가 맨 오른쪽에 있었다면
			{
				newNode.parent.r = newNode;
			} else {														// 그 index가 맨 오른쪽이 아니라면
				newNode.parent.p[index].value = newNode;
			}		
			//현재 합친 newNode의 parent가 루트일때, 루트의 m이 0이라면 newNode를 root로 바꾸고 종료, 루트의 m이 0이 아니라면 그냥 종료
			if(newNode.parent == this.root) {
				if(root.get_p_num() == 0) {
					this.root = newNode;
					return;
				}else
					return;
			}
			//nextNode는 newNode.paret가 되고
			Node nextNode = newNode.parent;
			//만약 nextNode의 m이 너무 작아지면 merge 또는 borrow를 시작한다.
			if(nextNode.get_p_num() < half(n+1) - 1) {			
				//nextNode가 부모 노드의 가장 오른쪽에 달려있으면 왼쪽과 합치던가 왼쪽에게 빌리고
				if(nextNode.parent.r == nextNode) {
					if(nextNode.pre.get_p_num() + 1 + nextNode.get_p_num() <= n) {
						merge_non_leaf(nextNode.pre,nextNode);
					}else {
						borrow_non_leaf(nextNode,0);
					}
				//nextNode가 부모 노드의 가장 오른쪽이 아니라면 오른쪽과 합치던가 오른쪽에게 빌린다.
				} else {
					if(nextNode.get_p_num() + 1 + nextNode.next.get_p_num() <= n) {
						merge_non_leaf(nextNode,nextNode.next);
					}else {
						borrow_non_leaf(nextNode,1);
					}
				}
			}
		}

		void borrow_leaf(Node node, int flag) {
			int index, middleKey;
			//node와 빌려주는 옆노드가 있다고 하면, node1은 왼쪽의 노드, node2는 오른쪽의 노드임.
			Node node1, node2; 
			//왼쪽에서 빌려오는 경우.
			if(flag == 0) {
				node1 = node.pre;
				node2 = node;
				//빌려오것 node2에 넣기
				node2.add(node1.p[node1.get_p_num()-1].key, node1.p[node1.get_p_num()-1].value);
				//node2의 대표키값 바꾸기
				exchange_nonLeaf(node2.p[1].key,node2.p[0].key);
				//빌려준것 node1에서 지우기
				node1.remove(node1.p[node1.get_p_num()-1].key);
			}
			//오른쪽에서 빌려오는 경우.
			else {
				node1 = node;
				node2 = node.next;
				//두 노드 사이의 middleKey 찾기
				index = node1.parent.search_path(node1);
				middleKey = node1.parent.p[index].key;
				//빌려오것 node1에 넣기
				node1.add(node2.p[0].key, node2.p[0].value);
				//node2의 대표키값 바꾸기
				exchange_nonLeaf(node2.p[0].key,node2.p[1].key);
				//빌려준것 node2에서 지우기
				node2.remove(node2.p[0].key);
			}
			
		}
		
		void merge_non_leaf(Node node1, Node node2) {
			Node newNode = new Node(n,false);
			newNode.id = node1.id;
			int i, j, index, middleKey;
			//node1의 값들 모두 넣기
			for(i = 0; i<node1.get_p_num(); i++) {
				newNode.p[i] = node1.p[i];
				((Node)newNode.p[i].value).parent = newNode;
				newNode.m++;
			}
			//node1과 node2 사이값 넣기
			index = node1.parent.search_path(node1);
			middleKey = node1.parent.p[index].key;
			newNode.add(middleKey, node1.r);
			node1.r.parent = newNode;
			//node2의 값을 모두 넣기
			for(j = newNode.get_p_num(), i = 0; i<node2.get_p_num(); i++, j++) {
				newNode.p[j] = node2.p[i];
				((Node)newNode.p[j].value).parent = newNode;
				newNode.m++;
			}
			newNode.r = node2.r;
			node2.r.parent = newNode;
			//newNode의 기타 값들 설정하기(parent,pre,next)
			newNode.pre = node1.pre;
			newNode.next = node2.next;
			newNode.parent = node1.parent;
			
			//newNode를 주변 node들과 연결하기
			if(newNode.pre != null)
				newNode.pre.next = newNode;
			if(newNode.next != null)
				newNode.next.pre = newNode;
			if((index = newNode.parent.remove(middleKey))==newNode.parent.get_p_num()) // 부모노드에서 middleKey 값을 지우면서 middleKey가 맨 오른쪽에 있었다면
			{
				newNode.parent.r = newNode;
			} else {														// 부모노드에서 middleKey 값을 지우면서 middleKey가 맨 오른쪽에 없었다면
				newNode.parent.p[index].value = newNode;
			}		
			//현재 합친 newNode의 parent가 루트일때, 루트의 m이 0이라면 newNode를 root로 바꾸고 종료, 루트의 m이 0이 아니라면 그냥 종료
			if(newNode.parent == this.root) {
				if(root.get_p_num() == 0) {
					this.root = newNode;
					return;
				}else
					return;
			}
			//nextNode는 newNode.paret가 되고
			Node nextNode = newNode.parent;
			//만약 nextNode의 m이 너무 작아지면 merge 또는 borrow를 시작한다.
			if(nextNode.get_p_num() < half(n+1) - 1) {			
				//nextNode가 부모 노드의 가장 오른쪽에 달려있으면 왼쪽과 합치던가 왼쪽에게 빌리고
				if(nextNode.parent.r == nextNode) {
					if(nextNode.pre.get_p_num() + 1 + nextNode.get_p_num() <= n) {
						merge_non_leaf(nextNode.pre,nextNode);
					}else {
						borrow_non_leaf(nextNode,0);
					}
				//nextNode가 부모 노드의 가장 오른쪽이 아니라면 오른쪽과 합치던가 오른쪽에게 빌린다.
				} else {
					if(nextNode.get_p_num() + 1 + nextNode.next.get_p_num() <= n) {
						merge_non_leaf(nextNode,nextNode.next);
					}else {
						borrow_non_leaf(nextNode,1);
					}
				}
			}
		}

		
		void borrow_non_leaf(Node node, int flag) {
			int index, middleKey;
			//node와 빌려주는 옆노드가 있다고 하면, node1은 왼쪽의 노드, node2는 오른쪽의 노드임.
			Node node1, node2; 
			//왼쪽에서 빌려오는 경우.
			if(flag == 0) {
				node1 = node.pre;
				node2 = node;
				//두 노드 사이의 middleKey 찾기
				index = node1.parent.search_path(node1);
				middleKey = node1.parent.p[index].key;
				//빌려오것 node2에 넣기
				node2.add(middleKey, node1.r);
				((Node)node2.p[0].value).parent = node2;
				//middleKey 중간값으로 재설정하기
				middleKey = node1.p[node1.get_p_num()-1].key;
				node1.parent.p[index].key = middleKey;
				//빌려준것 node1에서 지우기
				node1.r = (Node)node1.p[node1.get_p_num()-1].value;
				node1.remove(node1.p[node1.get_p_num()-1].key);
			}
			//오른쪽에서 빌려오는 경우.
			else {
				node1 = node;
				node2 = node.next;
				//두 노드 사이의 middleKey 찾기
				index = node1.parent.search_path(node1);
				middleKey = node1.parent.p[index].key;
				//빌려오것 node1에 넣기
				node1.add(middleKey, node1.r);
				node1.r=(Node)node2.p[0].value;
				node1.r.parent = node1;
				//middleKey 중간값으로 재설정하기
				middleKey = node2.p[0].key;
				node1.parent.p[index].key = middleKey;
				//빌려준것 node2에서 지우기
				node2.remove(node2.p[0].key);
			}
		}
		
		void single_search(int key) {
			Node navi = this.root;
			int index;
			
			while(navi.isLeaf == false) {
				System.out.print(navi.p[0].key);
				for(int i = 1; i < navi.get_p_num(); i++) {
					System.out.print(", "+navi.p[i].key);
				}
				System.out.print("\n");
				
				index = navi.search_path(key);
				if(index == navi.get_p_num())
					navi = navi.r;
				else
					navi = (Node)navi.p[index].value;
			}
			
			index = navi.search(key);
			if(index < 0) {
				System.out.println("NOT FOUND");
			} else {
				System.out.println(navi.p[index].value);
			}
		}
		
		void range_search(int key1, int key2) {
			Node navi = this.root;
			int index;
			if(key1>key2) {
				System.out.println("STRANGE RANGE");
				return;
			}
			while(navi.isLeaf == false) {
				index = navi.search_path(key1);
				if(index == navi.get_p_num())
					navi = navi.r;
				else
					navi = (Node)navi.p[index].value;
			}
			index = navi.search_path(key1-1);
			if(index==navi.get_p_num()) {
				navi = navi.r;
			} else {
				for(int i = index; i<navi.get_p_num(); i++) {
					System.out.println(navi.p[i].key + ", " + navi.p[i].value);
				}
				navi = navi.r;
			}
			
			while(navi != null) {
				for(int i = 0; i<navi.get_p_num(); i++) {
					if(navi.p[i].key > key2)
						return;
					System.out.println(navi.p[i].key + ", " + navi.p[i].value);
				}
				navi = navi.r;
			}
		}
		
		public int get_node_number()
		{
			int number = 0;
			Node navi = this.root;
			Node node = this.root;
			while(true) {
				navi = node;
				while(true) {
					number ++;
					if(navi.next == null)
						break;
					navi = navi.next;
				}
				if(node.isLeaf)
					break;
				node = (Node)node.p[0].value;
			}
			return number;
		}
		
		public String toString() {
			String ret = "";
			Node navi = this.root;
			Node node = this.root;
			while(true) {
				navi = node;
				while(true) {
					ret += navi;
					if(navi.next == null)
						break;
					ret += ",  ";
					navi = navi.next;
				}
				ret += "\n\n";
				if(node.isLeaf)
					break;
				node = (Node)node.p[0].value;
			}
			return ret;
		}
		
		void check (Node node) {
			
			System.out.println(node + "분석 시작");
			
			if(node.parent != null)
				System.out.println("parent = " + node.parent);
			if(node.pre != null)
				System.out.println("pre = " + node.pre);
			if(node.next != null)
				System.out.println("next = " + node.next);	
			
			
			for(int i=0; i < node.p.length && node.p[i] != null; i++) {
				System.out.println("p["+i+"]의 value =" + node.p[i].value);
			}
			System.out.println("r의 value="+node.r);
			return;
		}
		
		void leafcheck(int key) {
			Node node = root;
			int index;
			
			while(node.isLeaf == false) {
				check(node);
				index = node.search_path(key);
			
				if(index < node.get_p_num()) {
					node = (Node)node.p[index].value;
				} else {
					node = node.r;
				}
			}
			
			check(node);
		}
	}

	public static class Node  {
		int m;		// m is # of children(non leaf) or # of keys(leaf)
		Pair[] p;		// array of <keys, leaf_child_nodes>(non leaf) or <keys, values>(leaf)
		Node r;			//next Node(leaf) or child Node(leaf)
		
		int n;		// n is max of keys
		boolean isLeaf;		// this is Leaf Node or non Leaf Node
		Node parent;	//parent Node
		Node next;		//next Node
		Node pre;		//previous Node
		int id;		//노드의 id (index 파일을 위해)
		
		public Node(int n, boolean isLeaf) {
			this.m = 0;
			
			//node가 non leaf라면 m은 children 갯수이므로 element보다 1개 더 커야한다.
			if(isLeaf == false)
				this.m++;
			
			this.n = n;
			this.p = new Pair[n+1];			//꽉 찬 상태에서 새로운 key가 들어왔을 때 넣어준 후에 split하기 위해 1이 더 큼
			this.r = null;
			this.isLeaf = isLeaf;
			this.parent = null;
			this.next = null;
			this.pre = null;
		}
		
		//p에 들어있는 element갯수 반환.
		int get_p_num() {
			if(this.isLeaf)
				return m;
			else
				return m-1;
		}
		

		int add(int key, Object value) {
			int index;
			
			if(get_p_num()>n) {
				System.out.println("error// 배열크기를 넘어간 add함수 호출");
				return -1;
			}
			
			
			for(index = 0; p[index]!=null && p[index].key<=key; index++); // 새로운 값이 들어갈 index 계산
			
			for(int i = get_p_num(); index < i; i--) {
				p[i] = p[i-1];
			}
			
			p[index] = new Pair(key, value);
			
			this.m++;
			
			return index;
		}
		
		//key를 찾으려면 어느 index로 내려가야하는지 반환해주는 함수
		int search_path(int key) {
			int index;
			for(index = 0; index<n && p[index]!=null && p[index].key<=key; index++); // 해당 노드에서 아래로 내려가기 위한 key의 위치
			return index;
		}
		
		int search_path(Node node) {
			if(this.isLeaf == true) {
				System.out.println("error// leaf node에서 search_path 함수 사용");
				return -1;
			}
			int index;
			for(index = 0; index<n && p[index]!=null; index++) {
				if(p[index].value == node) {
					return index;
				}
			}
			if(this.r==node) {
				return index;
			}
			System.out.println("error//"+ this + "노드는" + node +"를 자식으로 가지지 않습니다");
			return -1;
		}
		
		//찾으면 index반환, 없으면 -1반환
		int search(int key) {
			int index;
			for (index = 0; index<get_p_num(); index++) {
				if(key == p[index].key)
					return index;
			}
			return -1;
		}
		
		Node split() {
			Node node2 = new Node(n, this.isLeaf);		//나뉘어서 만들어진 새로운 node;
			int i, j;
			if(get_p_num() < n) {
				System.out.println("error// node가 꽉차지 않았는데 split 사용");
				return null;
			}


			for(j = 0,i = half(n); i < get_p_num(); i++,j++) {
				node2.p[j] = this.p[i];
				
				if(this.isLeaf == false) {
					((Node)node2.p[j].value).parent = node2;
				}
			}
			node2.r = this.r;		
			if(node2.isLeaf == false)
				node2.r.parent = node2;
			
			if(this.isLeaf) {						//현재 노드가 leaf일 때
				this.r = node2;
				node2.m = j;
				this.m = half(n);
			} else {								//현재 노드가 non leaf일 때
				this.r = (Node)node2.p[0].value;
				((Node)node2.p[0].value).parent = this;
				node2.m = j+1;
				this.m = half(n)+1;
			}
			

			node2.next = this.next;
			if(node2.next != null)
				node2.next.pre = node2;
		
			node2.pre = this;
			this.next = node2;
			
			
			
			node2.parent = this.parent;
			
			
			
			for(i = half(n); i < p.length; i++) {
				p[i] = null;
			}
			
			
			return node2;
		}
		
		int remove(int key) {		//지워진 index값 반환, 실패하면 -1 반환
			int index, i;
			
			if(get_p_num() == 0) {
				return -1;
			}
			
			for(index = get_p_num()-1; index>0 && p[index].key != key; index--); 		// 지우려는 key값 찾기
			
			
			if(p[index] != null && p[index].key == key) {				//찾았으면 삭제
				for(i = index; i < get_p_num()-1; i++) {
					p[i] = p[i+1];
				}
				p[get_p_num()-1] = null;
				m--;
				return index;
			} else {
				return -1;
			}
		}
		
		
		
		
		
		public String toString() {
			String result = "";
			result += "(";
			for(int i=0; i < p.length; i++) {
				if(p[i]!=null)
					result += p[i].key + " ";
			}
			result += ") m:"+m+", id: "+id;
			return result;
		}
		
		public int half(int n) {
			int result;
			if(n%2 == 0){
				result = n/2;
			} else {
				result = n/2 + 1;
			}
			
			return result;
		}
	}
	
	public static class Pair  {
		Integer key;
		Object value;
		
		public Pair(int key, Object value) {
			this.key = key;
			this.value = value;
		}
		
		
		public String toString() {
			return "key: " + this.key +", value: " + this. value;
		}
	}
}
