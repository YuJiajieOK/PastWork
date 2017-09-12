package ObjectComparison;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.print.attribute.Size2DSyntax;
import javax.xml.crypto.Data;

import sun.misc.Sort;

public class Main {
	
	
	static float NB_epsilon = (float) 0.05;
	static float INS_epsilon = (float) 0.08;
	static int saliencyThreshold = 170;
	static float interSectionThreshold = (float) 0.3;
	static int numOfbins = 256;
	static int boxedge = 3;
    static int numbersOfpattern = 300;
	static int numFeatures = 512;
	static int layerNum =5;
	static int objectNum = 0;
	
	static String imageName = "n07745940_5782";
	static String catName = "n07745940";
	
	static String output = "C:\\research\\imageNetMix\\results\\extra6\\similarity_"+ imageName + ".txt";
	static String queryFile = "C:\\research\\imageNetMix\\extraTesting\\6\\query_" + catName + "\\image\\" + imageName +".JPEG";
	static String querySM = "C:\\research\\imageNetMix\\extraTesting\\6\\query_" + catName + "\\sm\\" + imageName+".jpg";
	static String queryFeature = "C:\\research\\imageNetMix\\extraTesting\\6\\query_" + catName + "\\f5\\" + imageName + ".txt";
	static String queryObjectPath = "C:\\research\\imageNetMix\\queryObjects\\";
    static String candidateImage = "C:\\research\\imageNetMix\\images\\";
    static String candidateFeature = "C:\\research\\imageNetMix\\f5\\";
    static String candidateSM = "C:\\research\\imageNetMix\\sm\\"; 
    
    static String num = "10";
    
    static String queryImage = "C:\\research\\imageNetMix\\queryimages\\"+num;
    static String queryF5= "C:\\research\\imageNetMix\\queryfeature\\"+num;
    static String querySmap = "C:\\research\\imageNetMix\\querysm\\"+num;
    
    static String outputPath = "C:\\research\\imageNetMix\\results\\all\\";
    
    
	
	/**
	 * @param args
	 */
	static int n;
	public static ArrayList<Integer> descriptiveNb(ArrayList<ArrayList<Integer>> patternGenerator, ArrayList<ArrayList<Integer>> SO, int row, int col, float epsilon, float FV[][]){
		
		ArrayList<Integer> dscpNb = new ArrayList<Integer>();	
		for (int i = 0;i<SO.get(row).size(); i++){
			float diff = 0;
			for (int k = 0; k<numFeatures; k++){
				diff = diff + Math.abs(FV[patternGenerator.get(row).get(col)][k]-FV[SO.get(row).get(i)][k]);
		   }
			diff = (float) (diff/(float)numFeatures);
			if (diff<=epsilon) {
				dscpNb.add(SO.get(row).get(i));
			}
	    }
		return dscpNb;	
	}
	
	
	
	public static int descriptiveInterSection(ArrayList<Integer> dnb1,ArrayList<Integer> dnb2,float epsilon,float featureVector1[][], float featureVector2[][]){
		        int interSection = 0;
			
				for (int i1 = 0; i1<dnb1.size(); i1++){
					for (int i2 = 0; i2<dnb2.size(); i2++){
						float diff = 0;
						for(int k = 0; k<numFeatures; k++){
							diff = diff + Math.abs(featureVector1[dnb1.get(i1)][k]-featureVector2[dnb2.get(i2)][k]);
						}
						diff = (float) (diff/((float)numFeatures));
						if (diff<epsilon) {
							interSection++;
							break;
						}
					}
				}
                for (int i2 = 0; i2 < dnb2.size();i2++){
                	for (int i1 = 0; i1 < dnb1.size(); i1++){
                		float diff = 0;
                		for (int k = 0; k<numFeatures; k++){
                			diff = diff + Math.abs(featureVector1[dnb1.get(i1)][k]-featureVector2[dnb2.get(i2)][k]);
                		}
						diff = (float) (diff/((float)numFeatures));
                		if (diff<epsilon) {
							interSection++;
							break;
						}
                	}
                }
		return interSection;	
	}
	
	public static int[] ArrayDscNB(ArrayList<ArrayList<Integer>> patternGenerator, ArrayList<ArrayList<Integer>> SO, int row, int col, float epsilon, float FV[][]) {
		
		int[]DscNB = new int[SO.get(row).size()];
		n = 0;
		DscNB[n] = patternGenerator.get(row).get(col);		
		for (int i = 0; i<SO.get(row).size();i++){
			if ((Math.abs(FV[patternGenerator.get(row).get(col)][0]-FV[SO.get(row).get(i)][0])<=epsilon)&&(Math.abs(FV[patternGenerator.get(row).get(col)][1]-FV[SO.get(row).get(i)][1])<=epsilon)&&(Math.abs(FV[patternGenerator.get(row).get(col)][2]-FV[SO.get(row).get(i)][2])<=epsilon)&&(Math.abs(FV[patternGenerator.get(row).get(col)][3]-FV[SO.get(row).get(i)][3])<=epsilon)) {
				n++;
				DscNB[n] = SO.get(row).get(i);
			}
		}	
		return DscNB;		
	}
	
	public static int  ArrayDInSc(int []dnb1,int []dnb2, int sizefor1, int sizefor2, float epsilon,float featureVector1[][],float featureVector2[][]) {
		int intersection = 0;
		
		for (int i1 = 0; i1<=sizefor1; i1++){
			for (int i2 = 0; i2<=sizefor2; i2++){
				if ((Math.abs(featureVector1[dnb1[i1]][0]-featureVector2[dnb2[i2]][0])<epsilon)&&(Math.abs(featureVector1[dnb1[i1]][1]-featureVector2[dnb2[i2]][1])<epsilon)&&(Math.abs(featureVector1[dnb1[i1]][2]-featureVector2[dnb2[i2]][2])<epsilon)&&(Math.abs(featureVector1[dnb1[i1]][3]-featureVector2[dnb2[i2]][3])<epsilon)) {
					intersection++;
					break;
				}
			}
		}
		
		for (int i2 = 0; i2<=sizefor2; i2++){
			for (int i1 = 0; i1<=sizefor1; i1++){
				if ((Math.abs(featureVector1[dnb1[i1]][0]-featureVector2[dnb2[i2]][0])<epsilon)&&(Math.abs(featureVector1[dnb1[i1]][1]-featureVector2[dnb2[i2]][1])<epsilon)&&(Math.abs(featureVector1[dnb1[i1]][2]-featureVector2[dnb2[i2]][2])<epsilon)&&(Math.abs(featureVector1[dnb1[i1]][3]-featureVector2[dnb2[i2]][3])<epsilon)) {
					intersection++;
					break;
				}
			}
		}
		return intersection;	
	}
	
	
	public static int nonePatterInterSection(ArrayList<Integer> qObject, ArrayList<Integer> canObject, float [][]qFV, float [][]canFV, float epsilon) {
		int interSection = 0;
		
		for (int i1 = 0; i1<qObject.size(); i1++){
			for (int i2 = 0; i2<canObject.size(); i2++){
				if ((Math.abs(qFV[qObject.get(i1)][0]-canFV[canObject.get(i2)][0])<=epsilon)&&(Math.abs(qFV[qObject.get(i1)][1]-canFV[canObject.get(i2)][1])<=epsilon)&&(Math.abs(qFV[qObject.get(i1)][2]-canFV[canObject.get(i2)][2])<=epsilon)&&(Math.abs(qFV[qObject.get(i1)][3]-canFV[canObject.get(i2)][3])<=epsilon)) {
					interSection++;
					break;
				}
			}
		}
		for (int i2 = 0; i2<canObject.size(); i2++){
			for (int i1 = 0; i1<qObject.size(); i1++){
				if ((Math.abs(qFV[qObject.get(i1)][0]-canFV[canObject.get(i2)][0])<=epsilon)&&(Math.abs(qFV[qObject.get(i1)][1]-canFV[canObject.get(i2)][1])<=epsilon)&&(Math.abs(qFV[qObject.get(i1)][2]-canFV[canObject.get(i2)][2])<=epsilon)&&(Math.abs(qFV[qObject.get(i1)][3]-canFV[canObject.get(i2)][3])<=epsilon)) {
					interSection++;
					break;
				}
			}
		}
		return interSection;
	}
	
	public static void main(String[] args) throws IOException {
		
		//Calculate on query image		
		//Shared parameter

		File qfiles = new File(queryImage);
		File q_f = new File(queryF5);
		File q_sm = new File(querySmap);
	
		File[] qFilesName = qfiles.listFiles();
		File[] qf = q_f.listFiles();
		File[] qsm = q_sm.listFiles();
	   for (int q = 0; q<qFilesName.length; q++){
		  if(qFilesName[q].isFile()&&qf[q].isFile()&&qsm[q].isFile()){
		
		File Qfiles=qFilesName[q];
		File smfile=qsm[q];
		
		
		ArrayList<ArrayList<Object>> Datas = new ArrayList<ArrayList<Object>>();
		
		int[][] sMap1 = IOclass.inputImage(smfile, 1);		
		int[][] DownsMap1 = tools.downSampling(sMap1);

		for (int i = 0; i<layerNum-2; i++){
			DownsMap1 = tools.downSampling(DownsMap1);
		}
		File qfFile = qf[q];
		float [][]qf5 = new float[(DownsMap1.length)*(DownsMap1[0].length)][numFeatures];
		qf5 = readTxt.Readtext(qfFile, (DownsMap1.length)*(DownsMap1[0].length), numFeatures);
		

		int row = sMap1.length;
		int col = sMap1[0].length;
		int row1 = DownsMap1.length;
		int col1 = DownsMap1[0].length;
		

		
		float[][] qfeatureVector = new float[row1*col1][numFeatures];			
		File qfile = qf[q];
		qfeatureVector = readTxt.Readtext(qfile, row1*col1, numFeatures);
		qfeatureVector = tools.normalization(qfeatureVector, numFeatures);
		

		ArrayList<ArrayList<Integer>> salienceBlock1 = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> DownsalienceBlock1 = new ArrayList<ArrayList<Integer>>();
			
		ArrayList<ArrayList<Integer>> DownpatternGenerators1 = new ArrayList<ArrayList<Integer>>();
		
		salienceBlock1 = SaliencyDetection.newSalienceDetection(sMap1, saliencyThreshold, qfeatureVector,numOfbins);
		DownsalienceBlock1 = SaliencyDetection.newSalienceDetection(DownsMap1, saliencyThreshold, qfeatureVector, numOfbins);
		
		
		//This is the object in query image we are going to look for in candidate images
	
		DownpatternGenerators1 = Pattern.NewPatternGenerators(DownsalienceBlock1, numbersOfpattern);

	    BufferedImage queryBufferedImage = new BufferedImage(col1,row1,BufferedImage.TYPE_INT_RGB);
	    
	    ArrayList<ArrayList<Integer>> aimtoFind = new ArrayList<ArrayList<Integer>>();
	    aimtoFind.add(salienceBlock1.get(objectNum));
	    
	    
	    queryBufferedImage = tools.addBox(Qfiles, row, col, aimtoFind,boxedge);
	    
	    
	       String ext="jpg";
	       String qString = Integer.toString(q) ;
	       String  filepath = queryObjectPath +"QueryObject" + qString + "." + ext;
	       try {
	           ImageIO.write(queryBufferedImage, ext,  new File(filepath));
	           System.out.println("File path is:" + filepath);
	       }
	       catch (IOException ex) {
	           ex.printStackTrace();
	       }		
		// Calculating on candidate images
	       
			long start = 0;
			long end = 0;
		    long time = 0;
			start = System.currentTimeMillis();//timer
		
		File files = new File(candidateImage);
		File files_f = new File(candidateFeature);
		File files_sm = new File(candidateSM);
	
		File[] candfFilesName = files.listFiles();
		File[] candf = files_f.listFiles();
		File[] candsm = files_sm.listFiles();
		
		int imagenumber = 0;
		
		for (int i = 0; i<candfFilesName.length; i++){

			imagenumber++;
			ArrayList<Integer> objectFound = null;
			System.out.println(imagenumber+"Start");
		     if(candfFilesName[i].isFile()&&candf[i].isFile()&&candsm[i].isFile()){
				ArrayList<Object> oneItem = new ArrayList<Object>();
		    	int countSelected = 0; 
		    	
		    	File ci = candfFilesName[i];
		    	
		    	String fileName = ci.getName();
		    	
				File cfv = candf[i];
//				File cfv4 = candf4[i];
				File csm = candsm[i];
				
                int [][]candsmap = IOclass.inputImage(csm, 1);
                int [][]Downcandsmap = tools.downSampling(candsmap);
                for (int l = 0; l<layerNum-2; l++){
                	Downcandsmap = tools.downSampling(Downcandsmap);
                }
                       
				int candRow = candsmap.length;
				int candCol = candsmap[0].length;
				
				int candRow1 = Downcandsmap.length;
				int candCol1 = Downcandsmap[0].length;
				

				float[][] cfeatureVector = readTxt.Readtext(cfv, candRow1*candCol1, numFeatures);
				cfeatureVector = tools.normalization(cfeatureVector, numFeatures);
				
				int [][]candImg = IOclass.inputImage(ci, 1);
				
				
				//Load RGB
				InputStream Inputimage = null;		
				try {
					Inputimage = new FileInputStream(ci);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch blockS
					e.printStackTrace();
				}
				BufferedImage Image = null;
				
				try {
					Image = ImageIO.read(Inputimage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				BufferedImage outputImage  = new BufferedImage(candCol, candRow, BufferedImage.TYPE_INT_RGB);
				int [][]finalMatrix = new int [candRow][candCol];
				// The for loop follow up can be optimized. It can be put into some other for loop.
				for (int x = 0; x<candRow; x++){
					for (int y = 0; y<candCol;y++){
						finalMatrix[x][y] = Image.getRGB(y, x);
					}
				}

				
				ArrayList<ArrayList<Integer>> CandsalienceBlock = new ArrayList<ArrayList<Integer>>();
				ArrayList<ArrayList<Integer>> DownCandsalienceBlock = new ArrayList<ArrayList<Integer>>();
				ArrayList<ArrayList<Integer>> DownpatternGeneratorsCand = new ArrayList<ArrayList<Integer>>();
				
				CandsalienceBlock = SaliencyDetection.newSalienceDetection(candsmap, saliencyThreshold,cfeatureVector,numOfbins);
				DownCandsalienceBlock = SaliencyDetection.newSalienceDetection(Downcandsmap, saliencyThreshold, cfeatureVector, numOfbins);
				DownpatternGeneratorsCand = Pattern.NewPatternGenerators(DownCandsalienceBlock, numbersOfpattern);
					    
			    ArrayList<Integer> dnb1 = null;
				ArrayList<Integer> dnb2 = null;
		
				float Pattern_similarity;
			    float MaxSimilarity = 0;
				int u1 = 0;
				int u2 = 0;
				objectFound = new ArrayList<Integer>();
//				dnb1 =new ArrayList<Integer>();				
			for (int j = 0; j<DownpatternGeneratorsCand.size(); j++){
					Pattern_similarity = 0;
					int counting = 0;
				for (int m = 0; m<DownpatternGenerators1.get(objectNum).size();m++){
					//Descriptive Neighbour in arraylist
					dnb1 =new ArrayList<Integer>();
					dnb1 = descriptiveNb(DownpatternGenerators1, DownsalienceBlock1, objectNum, m, NB_epsilon, qfeatureVector);
					u1 = dnb1.size();

					float similarity = 0;
					float s = 0;					
						for (int k = 0; k<DownpatternGeneratorsCand.get(j).size();k++){
							//Descriptive Neighbour in arraylist
							dnb2 =new ArrayList<Integer>();
							dnb2 = descriptiveNb(DownpatternGeneratorsCand, DownCandsalienceBlock, j, k, NB_epsilon, cfeatureVector);
							u2 = dnb2.size();
							int y = 0;
							//Descriptive intersection by arraylist
							y = descriptiveInterSection(dnb1, dnb2, INS_epsilon, qfeatureVector, cfeatureVector);
							s = (float)y/(float)(u1+u2);
							if (s==1) {
								similarity = s;
								break;
							}
							if (s>similarity) {
								similarity = s;
							}
						}
						Pattern_similarity = Pattern_similarity+similarity;
						}						

				Pattern_similarity = (float)Pattern_similarity/(DownpatternGenerators1.get(objectNum).size());
				if (Pattern_similarity>MaxSimilarity) {
					MaxSimilarity = Pattern_similarity;
				}
				System.out.println(Pattern_similarity);
				counting++;					
			}

			
			oneItem.add(fileName);
			oneItem.add(MaxSimilarity);
			
			Datas.add(oneItem);
		     }
		}
		end = System.currentTimeMillis();
		time = end-start;
		System.out.println(time);
		
		String outputName = outputPath +"similarity_"+ qf[q].getName();
		File outputData = new File(outputName);
		BufferedWriter bf =new BufferedWriter(new FileWriter(outputData));
		for (int i = 0; i<Datas.size(); i++){
			
			String fl = String.valueOf(Datas.get(i).get(1));
			bf.write((String) Datas.get(i).get(0));
			bf.write(" ");
			bf.write(fl);
			bf.newLine();
		}
		bf.close();
		System.out.println("Done");
		  }
	   }
		
	}

}


