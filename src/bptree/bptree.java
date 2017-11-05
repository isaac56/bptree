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
		
		//Ŀ�ǵ��� ����
		if(args.length == 0) {
			
			while(n<3) {
				System.out.println("Input new tree's degree: ");
				n = keyboard.nextInt();
				if(n<3)
					System.out.println("degree�� 3�̻� �̾�� �մϴ�");
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
			                // -1 �ɼ��� ������ "," ���� �� ���鵵 �б� ���� �ɼ�
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
			                // -1 �ɼ��� ������ "," ���� �� ���鵵 �б� ���� �ɼ�
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
					System.out.println("degree�� 3�̻� �̾�� �մϴ�");
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
		                // -1 �ɼ��� ������ "," ���� �� ���鵵 �б� ���� �ɼ�
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
		                // -1 �ɼ��� ������ "," ���� �� ���鵵 �б� ���� �ɼ�
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
					//�Ӽ� �߰��� ����� id ����
					oos.writeInt(navi.id);
					//p ����
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
					//r�� hash_key ����
					if(navi.r != null)
						oos.writeInt(navi.r.id);
					else
						oos.writeInt(-1);

					//parent�� hash_key ����
					if(navi.parent != null)
						oos.writeInt(navi.parent.id);
					else
						oos.writeInt(-1);

					//next�� hash_key ����
					if(navi.next != null)
						oos.writeInt(navi.next.id);
					else
						oos.writeInt(-1);

					//pre�� hash_key ����
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
            //������ p�� �ִ�ũ�� n�� Ʈ���� ��尹�� number_of_node �б�
            int n = ois.readInt();
            int number_of_node = ois.readInt();
            
            Node node;
            int id, m, r, parent, next, pre, key, value;
            boolean isLeaf;
            //tree ����.
            result = new Bptree(n+1);
            for(int i = 0; i<number_of_node; i++) {
            	//id, m, isLeaf �� �б�
            	id = ois.readInt();
            	m = ois.readInt();
            	
            	isLeaf = ois.readBoolean();
            	//hashMap�� �߰��ϱ�
            	index.put(id, new Node(n,isLeaf));
            	//�Ӽ� ���� node id�� �����ϱ�
            	node = (Node)index.get(id);
            	//node�� m �Ӽ� �б�
            	node.m = m;
            	//node�� id �Ӽ� �б�
            	node.id = id;
            }
            for(int i = 0; i<number_of_node; i++) {
            	//�Ӽ� ���� id �б�
            	id = ois.readInt();
            	//�Ӽ� ���� node id�� �����ϱ�
            	node = (Node)index.get(id);
            	//node�� p�Ӽ� �б�
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
            	//node�� r�Ӽ� �б�
            	r = ois.readInt();
            	if(r != -1)
            		node.r = (Node)index.get(r);
            	//node�� parent�Ӽ� �б�
            	parent = ois.readInt();
            	if(parent != -1)
            		node.parent = (Node)index.get(parent);
            	//node�� next�Ӽ� �б�
            	next = ois.readInt();
            	if(next != -1)
            		node.next = (Node)index.get(next);
            	//node�� pre�Ӽ� �б�
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
		int id = 0; //�� ������ id ������;

		public Bptree(int n) {
			this.n = n-1;
			this.root = new Node(n, true);
			this.root.id = this.id++;
		}
		
		void insert(int key, int value) {
			Node node = root;
			int index;
			
			//�ش� key�� ����ִ� leaf��� �����ϱ�
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
				
				//non leaf ����� ���� split �� ���λ��� ����� �� �� pair�� ���������Ѵ�.
				if(node2.isLeaf == false)
					node2.remove(node2.p[0].key);
				/////////////////////////////////////////////////////
			} else {
				node2 = node.split();
				node2.id = this.id++;
				
				parent = node.parent;
				
				int index = parent.search_path(node2.p[0].key);
				if(index+1 > parent.get_p_num()) {								//�θ��忡�� �� �����ʿ� �߰��� ��
					index = parent.add(node2.p[0].key, node);
					parent.r = node2;
				} else {												//�θ��忡�� �� �������� �ƴ� �� 
					index = parent.add(node2.p[0].key, node);
					parent.p[index+1].value = node2;
				}
				
				//non leaf ����� ���� split �� ���λ��� ����� �� �� pair�� ���������Ѵ�.
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

			//ã�� �� node�� root��� �׳� remove �Ѵ�.
			if(this.root == node) {		
				node.remove(key);
				return;
			}
		
			
			//�ش� key ������ �õ��ϰ� �����ϸ� �ʿ��� ��Ȳ�� �� ��ǥ Ű�� ��ü, �����ϸ� �׳� ����
			if((index = node.remove(key)) >= 0) {		
				//������ ���� leaf�� ���� ���� ���̶��
				if(index == 0) {
					//����� �� �� leaf�� element�� ���������� ���� element�� ���� ���� ���� ��ǥ Ű���� �ȴ�.
					if(node.get_p_num() > 0)
						exchange_nonLeaf(key, node.p[0].key);
					//����� �� �� leaf�� element�� �������� ������,
					else
					{
						//���� ���� ���ʿ� �޷� �ִٸ�
						if(node.parent.p[0].value == node) {
							exchange_nonLeaf(key, node.next.p[0].key);
						}
						//���� ���ʿ� �޷� ���� ������ ���� �ʿ䰡 ����.(merge_leaf �� �� �����ֱ� ������)
					}		
				}
			} 
			//������� key�� �������� ���� ��� �����޼��� ���
			else {										
				System.out.println("Error: �ش� key�� �������� ����");
				return;
			}
			//���� m�� �ʹ� �۾����� merge �Ǵ� borrow�� �����Ѵ�.
			if(node.get_p_num() < half(n)) {
				//���� node(leaf)�� parent�� ���� �����ʿ� �޷������� ���ʰ� ��ġ���� ���ʿ��� ������
				if(node.parent.r == node) {
					if(node.pre.get_p_num() + node.get_p_num()<=n) {
						merge_leaf(node.pre, node);
					}else {
						borrow_leaf(node,0);
					}
				//���� node(leaf)�� parent�� ���� �������� �ƴ϶�� �����ʰ� ��ġ���� �����ʿ��� ������.
				} else {
					if(node.get_p_num() + node.next.get_p_num()<=n) {
						merge_leaf(node, node.next);
					}else {
						borrow_leaf(node,1);
					}
				}
			}
		}
	
			
		//n�� 2�� ������ �ø��� ���� ����
		int half(int n) {
			int result;
			if(n%2 == 0){
				result = n/2;
			} else {
				result = n/2 + 1;
			}
			return result;
		}
		
		//non Leaf ���� �߿��� toChange�� key���� instead�� �ٲ۴�.
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
			//node1�� ���� ��� �ֱ�
			for(i = 0; i<node1.get_p_num(); i++) {
				newNode.p[i] = node1.p[i];
				newNode.m++;
			}
			//node2�� ���� ��� �ֱ�
			for(j = newNode.get_p_num(), i = 0; i<node2.get_p_num(); i++, j++) {
				newNode.p[j] = node2.p[i];
				newNode.m++;
			}
			newNode.r = node2.r;
			//newNode�� ��Ÿ ���� �����ϱ�(parent,pre,next)
			newNode.pre = node1.pre;
			newNode.next = node2.next;
			newNode.parent = node1.parent;
			
			//newNode�� �ֺ� node��� �����ϱ�
			if(newNode.pre != null)
				newNode.pre.next = newNode;
			if(newNode.next != null)
				newNode.next.pre = newNode;
			
			index = newNode.parent.search_path(node1);	//node1���� ���ϴ� index ã��
			if((index = newNode.parent.remove(newNode.parent.p[index].key))==newNode.parent.get_p_num()) // ������� �� index�� �� �����ʿ� �־��ٸ�
			{
				newNode.parent.r = newNode;
			} else {														// �� index�� �� �������� �ƴ϶��
				newNode.parent.p[index].value = newNode;
			}		
			//���� ��ģ newNode�� parent�� ��Ʈ�϶�, ��Ʈ�� m�� 0�̶�� newNode�� root�� �ٲٰ� ����, ��Ʈ�� m�� 0�� �ƴ϶�� �׳� ����
			if(newNode.parent == this.root) {
				if(root.get_p_num() == 0) {
					this.root = newNode;
					return;
				}else
					return;
			}
			//nextNode�� newNode.paret�� �ǰ�
			Node nextNode = newNode.parent;
			//���� nextNode�� m�� �ʹ� �۾����� merge �Ǵ� borrow�� �����Ѵ�.
			if(nextNode.get_p_num() < half(n+1) - 1) {			
				//nextNode�� �θ� ����� ���� �����ʿ� �޷������� ���ʰ� ��ġ���� ���ʿ��� ������
				if(nextNode.parent.r == nextNode) {
					if(nextNode.pre.get_p_num() + 1 + nextNode.get_p_num() <= n) {
						merge_non_leaf(nextNode.pre,nextNode);
					}else {
						borrow_non_leaf(nextNode,0);
					}
				//nextNode�� �θ� ����� ���� �������� �ƴ϶�� �����ʰ� ��ġ���� �����ʿ��� ������.
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
			//node�� �����ִ� ����尡 �ִٰ� �ϸ�, node1�� ������ ���, node2�� �������� �����.
			Node node1, node2; 
			//���ʿ��� �������� ���.
			if(flag == 0) {
				node1 = node.pre;
				node2 = node;
				//�������� node2�� �ֱ�
				node2.add(node1.p[node1.get_p_num()-1].key, node1.p[node1.get_p_num()-1].value);
				//node2�� ��ǥŰ�� �ٲٱ�
				exchange_nonLeaf(node2.p[1].key,node2.p[0].key);
				//�����ذ� node1���� �����
				node1.remove(node1.p[node1.get_p_num()-1].key);
			}
			//�����ʿ��� �������� ���.
			else {
				node1 = node;
				node2 = node.next;
				//�� ��� ������ middleKey ã��
				index = node1.parent.search_path(node1);
				middleKey = node1.parent.p[index].key;
				//�������� node1�� �ֱ�
				node1.add(node2.p[0].key, node2.p[0].value);
				//node2�� ��ǥŰ�� �ٲٱ�
				exchange_nonLeaf(node2.p[0].key,node2.p[1].key);
				//�����ذ� node2���� �����
				node2.remove(node2.p[0].key);
			}
			
		}
		
		void merge_non_leaf(Node node1, Node node2) {
			Node newNode = new Node(n,false);
			newNode.id = node1.id;
			int i, j, index, middleKey;
			//node1�� ���� ��� �ֱ�
			for(i = 0; i<node1.get_p_num(); i++) {
				newNode.p[i] = node1.p[i];
				((Node)newNode.p[i].value).parent = newNode;
				newNode.m++;
			}
			//node1�� node2 ���̰� �ֱ�
			index = node1.parent.search_path(node1);
			middleKey = node1.parent.p[index].key;
			newNode.add(middleKey, node1.r);
			node1.r.parent = newNode;
			//node2�� ���� ��� �ֱ�
			for(j = newNode.get_p_num(), i = 0; i<node2.get_p_num(); i++, j++) {
				newNode.p[j] = node2.p[i];
				((Node)newNode.p[j].value).parent = newNode;
				newNode.m++;
			}
			newNode.r = node2.r;
			node2.r.parent = newNode;
			//newNode�� ��Ÿ ���� �����ϱ�(parent,pre,next)
			newNode.pre = node1.pre;
			newNode.next = node2.next;
			newNode.parent = node1.parent;
			
			//newNode�� �ֺ� node��� �����ϱ�
			if(newNode.pre != null)
				newNode.pre.next = newNode;
			if(newNode.next != null)
				newNode.next.pre = newNode;
			if((index = newNode.parent.remove(middleKey))==newNode.parent.get_p_num()) // �θ��忡�� middleKey ���� ����鼭 middleKey�� �� �����ʿ� �־��ٸ�
			{
				newNode.parent.r = newNode;
			} else {														// �θ��忡�� middleKey ���� ����鼭 middleKey�� �� �����ʿ� �����ٸ�
				newNode.parent.p[index].value = newNode;
			}		
			//���� ��ģ newNode�� parent�� ��Ʈ�϶�, ��Ʈ�� m�� 0�̶�� newNode�� root�� �ٲٰ� ����, ��Ʈ�� m�� 0�� �ƴ϶�� �׳� ����
			if(newNode.parent == this.root) {
				if(root.get_p_num() == 0) {
					this.root = newNode;
					return;
				}else
					return;
			}
			//nextNode�� newNode.paret�� �ǰ�
			Node nextNode = newNode.parent;
			//���� nextNode�� m�� �ʹ� �۾����� merge �Ǵ� borrow�� �����Ѵ�.
			if(nextNode.get_p_num() < half(n+1) - 1) {			
				//nextNode�� �θ� ����� ���� �����ʿ� �޷������� ���ʰ� ��ġ���� ���ʿ��� ������
				if(nextNode.parent.r == nextNode) {
					if(nextNode.pre.get_p_num() + 1 + nextNode.get_p_num() <= n) {
						merge_non_leaf(nextNode.pre,nextNode);
					}else {
						borrow_non_leaf(nextNode,0);
					}
				//nextNode�� �θ� ����� ���� �������� �ƴ϶�� �����ʰ� ��ġ���� �����ʿ��� ������.
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
			//node�� �����ִ� ����尡 �ִٰ� �ϸ�, node1�� ������ ���, node2�� �������� �����.
			Node node1, node2; 
			//���ʿ��� �������� ���.
			if(flag == 0) {
				node1 = node.pre;
				node2 = node;
				//�� ��� ������ middleKey ã��
				index = node1.parent.search_path(node1);
				middleKey = node1.parent.p[index].key;
				//�������� node2�� �ֱ�
				node2.add(middleKey, node1.r);
				((Node)node2.p[0].value).parent = node2;
				//middleKey �߰������� �缳���ϱ�
				middleKey = node1.p[node1.get_p_num()-1].key;
				node1.parent.p[index].key = middleKey;
				//�����ذ� node1���� �����
				node1.r = (Node)node1.p[node1.get_p_num()-1].value;
				node1.remove(node1.p[node1.get_p_num()-1].key);
			}
			//�����ʿ��� �������� ���.
			else {
				node1 = node;
				node2 = node.next;
				//�� ��� ������ middleKey ã��
				index = node1.parent.search_path(node1);
				middleKey = node1.parent.p[index].key;
				//�������� node1�� �ֱ�
				node1.add(middleKey, node1.r);
				node1.r=(Node)node2.p[0].value;
				node1.r.parent = node1;
				//middleKey �߰������� �缳���ϱ�
				middleKey = node2.p[0].key;
				node1.parent.p[index].key = middleKey;
				//�����ذ� node2���� �����
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
			
			System.out.println(node + "�м� ����");
			
			if(node.parent != null)
				System.out.println("parent = " + node.parent);
			if(node.pre != null)
				System.out.println("pre = " + node.pre);
			if(node.next != null)
				System.out.println("next = " + node.next);	
			
			
			for(int i=0; i < node.p.length && node.p[i] != null; i++) {
				System.out.println("p["+i+"]�� value =" + node.p[i].value);
			}
			System.out.println("r�� value="+node.r);
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
		int id;		//����� id (index ������ ����)
		
		public Node(int n, boolean isLeaf) {
			this.m = 0;
			
			//node�� non leaf��� m�� children �����̹Ƿ� element���� 1�� �� Ŀ���Ѵ�.
			if(isLeaf == false)
				this.m++;
			
			this.n = n;
			this.p = new Pair[n+1];			//�� �� ���¿��� ���ο� key�� ������ �� �־��� �Ŀ� split�ϱ� ���� 1�� �� ŭ
			this.r = null;
			this.isLeaf = isLeaf;
			this.parent = null;
			this.next = null;
			this.pre = null;
		}
		
		//p�� ����ִ� element���� ��ȯ.
		int get_p_num() {
			if(this.isLeaf)
				return m;
			else
				return m-1;
		}
		

		int add(int key, Object value) {
			int index;
			
			if(get_p_num()>n) {
				System.out.println("error// �迭ũ�⸦ �Ѿ add�Լ� ȣ��");
				return -1;
			}
			
			
			for(index = 0; p[index]!=null && p[index].key<=key; index++); // ���ο� ���� �� index ���
			
			for(int i = get_p_num(); index < i; i--) {
				p[i] = p[i-1];
			}
			
			p[index] = new Pair(key, value);
			
			this.m++;
			
			return index;
		}
		
		//key�� ã������ ��� index�� ���������ϴ��� ��ȯ���ִ� �Լ�
		int search_path(int key) {
			int index;
			for(index = 0; index<n && p[index]!=null && p[index].key<=key; index++); // �ش� ��忡�� �Ʒ��� �������� ���� key�� ��ġ
			return index;
		}
		
		int search_path(Node node) {
			if(this.isLeaf == true) {
				System.out.println("error// leaf node���� search_path �Լ� ���");
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
			System.out.println("error//"+ this + "����" + node +"�� �ڽ����� ������ �ʽ��ϴ�");
			return -1;
		}
		
		//ã���� index��ȯ, ������ -1��ȯ
		int search(int key) {
			int index;
			for (index = 0; index<get_p_num(); index++) {
				if(key == p[index].key)
					return index;
			}
			return -1;
		}
		
		Node split() {
			Node node2 = new Node(n, this.isLeaf);		//����� ������� ���ο� node;
			int i, j;
			if(get_p_num() < n) {
				System.out.println("error// node�� ������ �ʾҴµ� split ���");
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
			
			if(this.isLeaf) {						//���� ��尡 leaf�� ��
				this.r = node2;
				node2.m = j;
				this.m = half(n);
			} else {								//���� ��尡 non leaf�� ��
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
		
		int remove(int key) {		//������ index�� ��ȯ, �����ϸ� -1 ��ȯ
			int index, i;
			
			if(get_p_num() == 0) {
				return -1;
			}
			
			for(index = get_p_num()-1; index>0 && p[index].key != key; index--); 		// ������� key�� ã��
			
			
			if(p[index] != null && p[index].key == key) {				//ã������ ����
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
