package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class cityTour2 {
	ArrayList<Vertex> nodes;
	public static void main(String [] args)
	{
		cityTour2 x = new cityTour2();
		ArrayList<Integer> beauty = new ArrayList<>();
		beauty.add(5); beauty.add(10); beauty.add(15); beauty.add(50);
		ArrayList<Integer> u = new ArrayList<>();
		u.add(0); u.add(1); u.add(0);
		ArrayList<Integer> v = new ArrayList<>();
		v.add(1); v.add(2); v.add(3);
		ArrayList<Integer> t = new ArrayList<>();
		t.add(6); t.add(7); t.add(10);
		x.addAll(4, 3, 30, beauty, u, v, t);
	}
	
	public void addAll(int vertices, int roads, int maxT, List<Integer> beauty, List<Integer> u, List<Integer> v, List<Integer> t)
	{
		nodes = new ArrayList<>();
		for(int i = 0 ; i < vertices; i++)
		{
			nodes.add(new Vertex(i, beauty.get(i)));
		}
		for(int i = 0; i < u.size(); i++)
		{
			int addTo = u.get(i);
			int addMe = v.get(i);
			nodes.get(addTo).addRoad(nodes.get(addMe), t.get(i));
		}
		for(int i = 0 ; i < vertices; i++)
		{
			//System.out.println(nodes.get(i));
		}
		//int longest = findMostBeauty(nodes.get(0), maxT);
		int longest = findMostBeauty2(nodes.get(0), maxT);
		//System.out.println(longest);
	}
	
	private int findMostBeauty2(Vertex vertex, int maxT) {
		int time = 0, prevTime = 0;
		int beauty = vertex.beauty, prevBeauty = 0;
		Stack<stackMe> vals = new Stack<>();
		stackMe start = new stackMe(vertex, -1, 0);
		for(int i = 0; i < vertex.path.size(); i++)
		{
			vertex.visited = 1;
			Vertex movingTo = vertex.path.get(i).endPoint;
			if(movingTo.visited == 0)
			{
				if(time + vertex.path.get(i).weight <= maxT)
				{
					vals.push(new stackMe(vertex, vertex.path.get(i).weight, i));
					time += vertex.path.get(i).weight;
					movingTo.parent = vertex;
					vertex = movingTo;
					beauty += vertex.beauty;
					i = -1;
				}
				else 
				{
					time = maxT+1;
				}
			}
			else if(visitAll(vertex))
			{
				//System.out.println("caused by "+vertex);
				//System.out.println(visitAll(vertex));
				stackMe back = vals.pop();
				time += back.weight;
				vertex = back.vertex;
				i = 0;
			}
			if(time > maxT)
			{
				//System.out.println("beaut"+beauty);
				if(beauty > prevBeauty)
				{
					prevBeauty = beauty;
					prevTime = time;
				}
				while(!vals.isEmpty())
				{
					stackMe b = vals.pop();
					vertex = b.vertex;
					i = b.i;
				}
				i = start.i;
				start.i++;
				//System.out.println("startOver "+ vertex+" "+i);
				resetAll(vertex);
				time = 0;
				beauty = vertex.beauty;
			}
		}
		System.out.println("beauty "+prevBeauty);
		return beauty;
	}
	
	private void resetAll(Vertex vertex) 
	{
		for(Vertex v : nodes)
			v.visited = 0;
	}

	private boolean visitAll(Vertex vertex) {
		for(Path r : vertex.path)
		{
			//System.out.println("connect "+r.endPoint + ". > " + r.endPoint.visited );
			if(r.endPoint.visited == 0)
				return false;
		}
		return true;
	}
	
	class stackMe
	{
		int weight;
		Vertex vertex;
		int i;
		stackMe(Vertex v, int w, int b)
		{
			weight = w;
			vertex = v;
			i = b;
		}
	}

	class Vertex
	{
		Vertex parent;
		int visited;
		int number;
		int beauty;
		ArrayList<Path> path;
		Vertex(int n, int b)
		{
			number = n;
			beauty = b;
			path = new ArrayList<>();
			visited = 0;
		}
		
		Vertex getParent()
		{
			return parent;
		}
		
		void setParent(Vertex v) 
		{
			parent = v;
		}
		
		public String toString()
		{
			String str = number+"/"+beauty+". Connects ";
			for(Path r : path)
				str += r.endPoint.number+" by "+r.weight+", ";
			return str;
		}
		
	    public boolean equals(Object obj) 
	    {
	    	Vertex v = (Vertex) obj;
	    	if(v.number == this.number)
	    		return true;
	    	return false;
	    }
		
		void addRoad(Vertex v, int t)
		{
			path.add(new Path(v, t));
			v.path.add(new Path(this, t));
		}
	}
	class Path
	{
		Vertex endPoint;
		int weight;
		Path(Vertex v, int w)
		{
			endPoint = v;
			weight = w;
		}
	}
}
