package test;

import java.util.*;
import java.util.Map.Entry;

public class cityTour {

	public static void main(String [] args)
	{
		cityTour x = new cityTour();
		ArrayList<Integer> beauty = new ArrayList<>();
		beauty.add(5); beauty.add(10); beauty.add(15); beauty.add(20);
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
		ArrayList<Vertex> nodes = new ArrayList<>();
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
			System.out.println(nodes.get(i));
		}
		int longest = findMostBeauty(nodes.get(0), maxT);
		System.out.println(longest);
	}
	
	private int findMostBeauty(Vertex vertex, int maxT) 
	{
		Stack<Road> path = new Stack<>();
		int prevTime = 0, prevBeauty = 0;
		int time = 0, beauty = 0; 
		for(int i = 0 ; i < vertex.road.size(); i++)
		{
			path.push(new Road(vertex, i));
			if(time + vertex.road.g && beauty > prevBeauty)
			{
				prevBeauty = beauty;
				continue;
			}
			else if(time > maxT)
			{
				
			}
		}
		return prevTime;
	}

	class Vertex
	{
		int curr;
		int beauty;
		private ArrayList<Road> road;
		Vertex(int num, int b)
		{
			curr = num;
			beauty = b;
			road = new ArrayList<>();
		}
		void addRoad(Vertex v, int w)
		{
			this.road.add(new Road(v, w));
			v.road.add(new Road(this, w));
		}
		public String toString()
		{
			String str = curr+"/"+beauty+". Connects ";
			for(Road r : road)
				str += r.v.curr+" by "+r.time+", ";
			return str;
		}
	}
	
	class Road
	{
		Vertex v;
		int time;
		Road(Vertex vert, int t)
		{
			v = vert;
			time = t;
		}
	}
}
