// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AabbUtil.java

package ic2.core.util;

import ic2.api.Direction;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class AabbUtil
{
	static final class Edge extends Enum
	{

		public static final Edge AD;
		public static final Edge AB;
		public static final Edge AE;
		public static final Edge DC;
		public static final Edge DH;
		public static final Edge BC;
		public static final Edge BF;
		public static final Edge EH;
		public static final Edge EF;
		public static final Edge CG;
		public static final Edge FG;
		public static final Edge HG;
		private static final Edge $VALUES[];

		public static Edge[] values()
		{
			return (Edge[])$VALUES.clone();
		}

		public static Edge valueOf(String name)
		{
			return (Edge)Enum.valueOf(ic2/core/util/AabbUtil$Edge, name);
		}

		static 
		{
			AD = new Edge("AD", 0);
			AB = new Edge("AB", 1);
			AE = new Edge("AE", 2);
			DC = new Edge("DC", 3);
			DH = new Edge("DH", 4);
			BC = new Edge("BC", 5);
			BF = new Edge("BF", 6);
			EH = new Edge("EH", 7);
			EF = new Edge("EF", 8);
			CG = new Edge("CG", 9);
			FG = new Edge("FG", 10);
			HG = new Edge("HG", 11);
			$VALUES = (new Edge[] {
				AD, AB, AE, DC, DH, BC, BF, EH, EF, CG, 
				FG, HG
			});
		}

		private Edge(String s, int i)
		{
			super(s, i);
		}
	}


	public AabbUtil()
	{
	}

	public static Direction getIntersection(Vec3 origin, Vec3 direction, AxisAlignedBB bbox, Vec3 intersection)
	{
		double length = direction.lengthVector();
		Vec3 normalizedDirection = Vec3.createVectorHelper(direction.xCoord / length, direction.yCoord / length, direction.zCoord / length);
		Direction intersectingDirection = intersects(origin, normalizedDirection, bbox);
		if (intersectingDirection == null)
			return null;
		Vec3 planeOrigin;
		if (normalizedDirection.xCoord < 0.0D && normalizedDirection.yCoord < 0.0D && normalizedDirection.zCoord < 0.0D)
			planeOrigin = Vec3.createVectorHelper(bbox.maxX, bbox.maxY, bbox.maxZ);
		else
		if (normalizedDirection.xCoord < 0.0D && normalizedDirection.yCoord < 0.0D && normalizedDirection.zCoord >= 0.0D)
			planeOrigin = Vec3.createVectorHelper(bbox.maxX, bbox.maxY, bbox.minZ);
		else
		if (normalizedDirection.xCoord < 0.0D && normalizedDirection.yCoord >= 0.0D && normalizedDirection.zCoord < 0.0D)
			planeOrigin = Vec3.createVectorHelper(bbox.maxX, bbox.minY, bbox.maxZ);
		else
		if (normalizedDirection.xCoord < 0.0D && normalizedDirection.yCoord >= 0.0D && normalizedDirection.zCoord >= 0.0D)
			planeOrigin = Vec3.createVectorHelper(bbox.maxX, bbox.minY, bbox.minZ);
		else
		if (normalizedDirection.xCoord >= 0.0D && normalizedDirection.yCoord < 0.0D && normalizedDirection.zCoord < 0.0D)
			planeOrigin = Vec3.createVectorHelper(bbox.minX, bbox.maxY, bbox.maxZ);
		else
		if (normalizedDirection.xCoord >= 0.0D && normalizedDirection.yCoord < 0.0D && normalizedDirection.zCoord >= 0.0D)
			planeOrigin = Vec3.createVectorHelper(bbox.minX, bbox.maxY, bbox.minZ);
		else
		if (normalizedDirection.xCoord >= 0.0D && normalizedDirection.yCoord >= 0.0D && normalizedDirection.zCoord < 0.0D)
			planeOrigin = Vec3.createVectorHelper(bbox.minX, bbox.minY, bbox.maxZ);
		else
			planeOrigin = Vec3.createVectorHelper(bbox.minX, bbox.minY, bbox.minZ);
		Vec3 planeNormalVector = null;
		static class 1
		{

			static final int $SwitchMap$ic2$api$Direction[];
			static final int $SwitchMap$ic2$core$util$AabbUtil$Edge[];

			static 
			{
				$SwitchMap$ic2$core$util$AabbUtil$Edge = new int[Edge.values().length];
				try
				{
					$SwitchMap$ic2$core$util$AabbUtil$Edge[Edge.AD.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$core$util$AabbUtil$Edge[Edge.AB.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$core$util$AabbUtil$Edge[Edge.AE.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$core$util$AabbUtil$Edge[Edge.DC.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$core$util$AabbUtil$Edge[Edge.DH.ordinal()] = 5;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$core$util$AabbUtil$Edge[Edge.BC.ordinal()] = 6;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$core$util$AabbUtil$Edge[Edge.BF.ordinal()] = 7;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$core$util$AabbUtil$Edge[Edge.EH.ordinal()] = 8;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$core$util$AabbUtil$Edge[Edge.EF.ordinal()] = 9;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$core$util$AabbUtil$Edge[Edge.CG.ordinal()] = 10;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$core$util$AabbUtil$Edge[Edge.FG.ordinal()] = 11;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$core$util$AabbUtil$Edge[Edge.HG.ordinal()] = 12;
				}
				catch (NoSuchFieldError ex) { }
				$SwitchMap$ic2$api$Direction = new int[Direction.values().length];
				try
				{
					$SwitchMap$ic2$api$Direction[Direction.XN.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$api$Direction[Direction.XP.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$api$Direction[Direction.YN.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$api$Direction[Direction.YP.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$api$Direction[Direction.ZN.ordinal()] = 5;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$ic2$api$Direction[Direction.ZP.ordinal()] = 6;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (1..SwitchMap.ic2.api.Direction[intersectingDirection.ordinal()])
		{
		case 1: // '\001'
		case 2: // '\002'
			planeNormalVector = Vec3.createVectorHelper(1.0D, 0.0D, 0.0D);
			break;

		case 3: // '\003'
		case 4: // '\004'
			planeNormalVector = Vec3.createVectorHelper(0.0D, 1.0D, 0.0D);
			break;

		case 5: // '\005'
		case 6: // '\006'
			planeNormalVector = Vec3.createVectorHelper(0.0D, 0.0D, 1.0D);
			break;
		}
		Vec3 newIntersection = getIntersectionWithPlane(origin, normalizedDirection, planeOrigin, planeNormalVector);
		intersection.xCoord = newIntersection.xCoord;
		intersection.yCoord = newIntersection.yCoord;
		intersection.zCoord = newIntersection.zCoord;
		return intersectingDirection;
	}

	public static Direction intersects(Vec3 origin, Vec3 direction, AxisAlignedBB bbox)
	{
		double ray[] = getRay(origin, direction);
		if (direction.xCoord < 0.0D && direction.yCoord < 0.0D && direction.zCoord < 0.0D)
		{
			if (origin.xCoord < bbox.minX)
				return null;
			if (origin.yCoord < bbox.minY)
				return null;
			if (origin.zCoord < bbox.minZ)
				return null;
			if (side(ray, getEdgeRay(Edge.EF, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.EH, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.DH, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.DC, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.BC, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.BF, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.HG, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.FG, bbox)) < 0.0D)
				return Direction.ZP;
			if (side(ray, getEdgeRay(Edge.CG, bbox)) < 0.0D)
				return Direction.YP;
			else
				return Direction.XP;
		}
		if (direction.xCoord < 0.0D && direction.yCoord < 0.0D && direction.zCoord >= 0.0D)
		{
			if (origin.xCoord < bbox.minX)
				return null;
			if (origin.yCoord < bbox.minY)
				return null;
			if (origin.zCoord > bbox.maxZ)
				return null;
			if (side(ray, getEdgeRay(Edge.HG, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.DH, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.AD, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.AB, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.BF, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.FG, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.DC, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.CG, bbox)) > 0.0D)
				return Direction.XP;
			if (side(ray, getEdgeRay(Edge.BC, bbox)) < 0.0D)
				return Direction.YP;
			else
				return Direction.ZN;
		}
		if (direction.xCoord < 0.0D && direction.yCoord >= 0.0D && direction.zCoord < 0.0D)
		{
			if (origin.xCoord < bbox.minX)
				return null;
			if (origin.yCoord > bbox.maxY)
				return null;
			if (origin.zCoord < bbox.minZ)
				return null;
			if (side(ray, getEdgeRay(Edge.FG, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.EF, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.AE, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.AD, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.DC, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.CG, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.EH, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.HG, bbox)) > 0.0D)
				return Direction.ZP;
			if (side(ray, getEdgeRay(Edge.DH, bbox)) < 0.0D)
				return Direction.XP;
			else
				return Direction.YN;
		}
		if (direction.xCoord < 0.0D && direction.yCoord >= 0.0D && direction.zCoord >= 0.0D)
		{
			if (origin.xCoord < bbox.minX)
				return null;
			if (origin.yCoord > bbox.maxY)
				return null;
			if (origin.zCoord > bbox.maxZ)
				return null;
			if (side(ray, getEdgeRay(Edge.EH, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.AE, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.AB, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.BC, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.CG, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.HG, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.AD, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.DH, bbox)) > 0.0D)
				return Direction.YN;
			if (side(ray, getEdgeRay(Edge.DC, bbox)) < 0.0D)
				return Direction.ZN;
			else
				return Direction.XP;
		}
		if (direction.xCoord >= 0.0D && direction.yCoord < 0.0D && direction.zCoord < 0.0D)
		{
			if (origin.xCoord > bbox.maxX)
				return null;
			if (origin.yCoord < bbox.minY)
				return null;
			if (origin.zCoord < bbox.minZ)
				return null;
			if (side(ray, getEdgeRay(Edge.AB, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.AE, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.EH, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.HG, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.CG, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.BC, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.EF, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.BF, bbox)) < 0.0D)
				return Direction.XN;
			if (side(ray, getEdgeRay(Edge.FG, bbox)) < 0.0D)
				return Direction.ZP;
			else
				return Direction.YP;
		}
		if (direction.xCoord >= 0.0D && direction.yCoord < 0.0D && direction.zCoord >= 0.0D)
		{
			if (origin.xCoord > bbox.maxX)
				return null;
			if (origin.yCoord < bbox.minY)
				return null;
			if (origin.zCoord > bbox.maxZ)
				return null;
			if (side(ray, getEdgeRay(Edge.DC, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.AD, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.AE, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.EF, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.FG, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.CG, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.AB, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.BC, bbox)) > 0.0D)
				return Direction.ZN;
			if (side(ray, getEdgeRay(Edge.BF, bbox)) < 0.0D)
				return Direction.XN;
			else
				return Direction.YP;
		}
		if (direction.xCoord >= 0.0D && direction.yCoord >= 0.0D && direction.zCoord < 0.0D)
		{
			if (origin.xCoord > bbox.maxX)
				return null;
			if (origin.yCoord > bbox.maxY)
				return null;
			if (origin.zCoord < bbox.minZ)
				return null;
			if (side(ray, getEdgeRay(Edge.BF, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.AB, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.AD, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.DH, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.HG, bbox)) < 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.FG, bbox)) > 0.0D)
				return null;
			if (side(ray, getEdgeRay(Edge.AE, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.EF, bbox)) > 0.0D)
				return Direction.XN;
			if (side(ray, getEdgeRay(Edge.EH, bbox)) < 0.0D)
				return Direction.YN;
			else
				return Direction.ZP;
		}
		if (origin.xCoord > bbox.maxX)
			return null;
		if (origin.yCoord > bbox.maxY)
			return null;
		if (origin.zCoord > bbox.maxZ)
			return null;
		if (side(ray, getEdgeRay(Edge.EF, bbox)) < 0.0D)
			return null;
		if (side(ray, getEdgeRay(Edge.EH, bbox)) > 0.0D)
			return null;
		if (side(ray, getEdgeRay(Edge.DH, bbox)) < 0.0D)
			return null;
		if (side(ray, getEdgeRay(Edge.DC, bbox)) > 0.0D)
			return null;
		if (side(ray, getEdgeRay(Edge.BC, bbox)) < 0.0D)
			return null;
		if (side(ray, getEdgeRay(Edge.BF, bbox)) > 0.0D)
			return null;
		if (side(ray, getEdgeRay(Edge.AB, bbox)) < 0.0D && side(ray, getEdgeRay(Edge.AE, bbox)) > 0.0D)
			return Direction.XN;
		if (side(ray, getEdgeRay(Edge.AD, bbox)) < 0.0D)
			return Direction.ZN;
		else
			return Direction.YN;
	}

	private static double[] getRay(Vec3 origin, Vec3 direction)
	{
		double ret[] = new double[6];
		ret[0] = origin.xCoord * direction.yCoord - direction.xCoord * origin.yCoord;
		ret[1] = origin.xCoord * direction.zCoord - direction.xCoord * origin.zCoord;
		ret[2] = -direction.xCoord;
		ret[3] = origin.yCoord * direction.zCoord - direction.yCoord * origin.zCoord;
		ret[4] = -direction.zCoord;
		ret[5] = direction.yCoord;
		return ret;
	}

	private static double[] getEdgeRay(Edge edge, AxisAlignedBB bbox)
	{
		switch (1..SwitchMap.ic2.core.util.AabbUtil.Edge[edge.ordinal()])
		{
		case 1: // '\001'
			return (new double[] {
				-bbox.minY, -bbox.minZ, -1D, 0.0D, 0.0D, 0.0D
			});

		case 2: // '\002'
			return (new double[] {
				bbox.minX, 0.0D, 0.0D, -bbox.minZ, 0.0D, 1.0D
			});

		case 3: // '\003'
			return (new double[] {
				0.0D, bbox.minX, 0.0D, bbox.minY, -1D, 0.0D
			});

		case 4: // '\004'
			return (new double[] {
				bbox.maxX, 0.0D, 0.0D, -bbox.minZ, 0.0D, 1.0D
			});

		case 5: // '\005'
			return (new double[] {
				0.0D, bbox.maxX, 0.0D, bbox.minY, -1D, 0.0D
			});

		case 6: // '\006'
			return (new double[] {
				-bbox.maxY, -bbox.minZ, -1D, 0.0D, 0.0D, 0.0D
			});

		case 7: // '\007'
			return (new double[] {
				0.0D, bbox.minX, 0.0D, bbox.maxY, -1D, 0.0D
			});

		case 8: // '\b'
			return (new double[] {
				-bbox.minY, -bbox.maxZ, -1D, 0.0D, 0.0D, 0.0D
			});

		case 9: // '\t'
			return (new double[] {
				bbox.minX, 0.0D, 0.0D, -bbox.maxZ, 0.0D, 1.0D
			});

		case 10: // '\n'
			return (new double[] {
				0.0D, bbox.maxX, 0.0D, bbox.maxY, -1D, 0.0D
			});

		case 11: // '\013'
			return (new double[] {
				-bbox.maxY, -bbox.maxZ, -1D, 0.0D, 0.0D, 0.0D
			});

		case 12: // '\f'
			return (new double[] {
				bbox.maxX, 0.0D, 0.0D, -bbox.maxZ, 0.0D, 1.0D
			});
		}
		return new double[0];
	}

	private static double side(double ray1[], double ray2[])
	{
		return ray1[2] * ray2[3] + ray1[5] * ray2[1] + ray1[4] * ray2[0] + ray1[1] * ray2[5] + ray1[0] * ray2[4] + ray1[3] * ray2[2];
	}

	private static Vec3 getIntersectionWithPlane(Vec3 origin, Vec3 direction, Vec3 planeOrigin, Vec3 planeNormalVector)
	{
		double distance = getDistanceToPlane(origin, direction, planeOrigin, planeNormalVector);
		return Vec3.createVectorHelper(origin.xCoord + direction.xCoord * distance, origin.yCoord + direction.yCoord * distance, origin.zCoord + direction.zCoord * distance);
	}

	private static double getDistanceToPlane(Vec3 origin, Vec3 direction, Vec3 planeOrigin, Vec3 planeNormalVector)
	{
		Vec3 base = Vec3.createVectorHelper(planeOrigin.xCoord - origin.xCoord, planeOrigin.yCoord - origin.yCoord, planeOrigin.zCoord - origin.zCoord);
		return dotProduct(base, planeNormalVector) / dotProduct(direction, planeNormalVector);
	}

	private static double dotProduct(Vec3 a, Vec3 b)
	{
		return a.xCoord * b.xCoord + a.yCoord * b.yCoord + a.zCoord * b.zCoord;
	}
}
