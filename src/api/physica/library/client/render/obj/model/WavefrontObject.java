package physica.library.client.render.obj.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import physica.library.client.render.TessellatorWrapper;

@SideOnly(Side.CLIENT)
public class WavefrontObject {
	private static Pattern vertexPattern = Pattern.compile("(v( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *\\n)|(v( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *$)");
	private static Pattern vertexNormalPattern = Pattern.compile("(vn( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *\\n)|(vn( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *$)");
	private static Pattern textureCoordinatePattern = Pattern.compile("(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *\\n)|(vt( (\\-){0,1}\\d+(\\.\\d+)?){2,3} *$)");
	private static Pattern face_V_VT_VN_Pattern = Pattern.compile("(f( \\d+/\\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+/\\d+){3,4} *$)");
	private static Pattern face_V_VT_Pattern = Pattern.compile("(f( \\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+){3,4} *$)");
	private static Pattern face_V_VN_Pattern = Pattern.compile("(f( \\d+//\\d+){3,4} *\\n)|(f( \\d+//\\d+){3,4} *$)");
	private static Pattern face_V_Pattern = Pattern.compile("(f( \\d+){3,4} *\\n)|(f( \\d+){3,4} *$)");
	private static Pattern groupObjectPattern = Pattern.compile("([go]( [\\w\\d\\.]+) *\\n)|([go]( [\\w\\d\\.]+) *$)");

	private static Matcher vertexMatcher, vertexNormalMatcher, textureCoordinateMatcher;
	private static Matcher face_V_VT_VN_Matcher, face_V_VT_Matcher, face_V_VN_Matcher, face_V_Matcher;
	private static Matcher groupObjectMatcher;

	public ArrayList<Vertex> vertices = new ArrayList<>();
	public ArrayList<Vertex> vertexNormals = new ArrayList<>();
	public ArrayList<TextureCoordinate> textureCoordinates = new ArrayList<>();
	public ArrayList<GroupObject> groupObjects = new ArrayList<>();
	private GroupObject currentGroupObject;
	private String fileName;

	public WavefrontObject(ResourceLocation resource) throws Exception {
		fileName = resource.toString();

		try {
			IResource res = Minecraft.getMinecraft().getResourceManager().getResource(resource);
			loadObjModel(res.getInputStream());
		} catch (IOException e) {
			throw new Exception("IO Exception reading model format", e);
		}
	}

	public WavefrontObject(String filename, InputStream inputStream) throws Exception {
		fileName = filename;
		loadObjModel(inputStream);
	}

	private void loadObjModel(InputStream inputStream) throws Exception {
		BufferedReader reader = null;

		String currentLine = null;
		int lineCount = 0;

		try {
			reader = new BufferedReader(new InputStreamReader(inputStream));

			while ((currentLine = reader.readLine()) != null) {
				lineCount++;
				currentLine = currentLine.replaceAll("\\s+", " ").trim();

				if (currentLine.startsWith("#") || currentLine.length() == 0) {
					continue;
				} else if (currentLine.startsWith("v ")) {
					Vertex vertex = parseVertex(currentLine, lineCount);
					if (vertex != null) {
						vertices.add(vertex);
					}
				} else if (currentLine.startsWith("vn ")) {
					Vertex vertex = parseVertexNormal(currentLine, lineCount);
					if (vertex != null) {
						vertexNormals.add(vertex);
					}
				} else if (currentLine.startsWith("vt ")) {
					TextureCoordinate textureCoordinate = parseTextureCoordinate(currentLine, lineCount);
					if (textureCoordinate != null) {
						textureCoordinates.add(textureCoordinate);
					}
				} else if (currentLine.startsWith("f ")) {

					if (currentGroupObject == null) {
						currentGroupObject = new GroupObject("Default");
					}

					ModelFace face = parseFace(currentLine, lineCount);

					if (face != null) {
						currentGroupObject.faces.add(face);
					}
				} else if (currentLine.startsWith("g ") | currentLine.startsWith("o ")) {
					GroupObject group = parseGroupObject(currentLine, lineCount);

					if (group != null) {
						if (currentGroupObject != null) {
							groupObjects.add(currentGroupObject);
						}
					}

					currentGroupObject = group;
				}
			}

			groupObjects.add(currentGroupObject);
		} catch (IOException e) {
			throw new Exception("IO Exception reading model format", e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
			}

			try {
				inputStream.close();
			} catch (IOException e) {
			}
		}
	}

	public void render() {
		TessellatorWrapper tessellator = TessellatorWrapper.instance;

		if (currentGroupObject != null) {
			tessellator.startDrawing(currentGroupObject.glDrawingMode);
		} else {
			tessellator.startDrawing(GL11.GL_TRIANGLES);
		}
		for (GroupObject groupObject : groupObjects) {
			groupObject.render(tessellator);
		}
		tessellator.draw();
	}

	private Vertex parseVertex(String line, int lineCount) throws Exception {
		Vertex vertex = null;

		if (isValidVertexLine(line)) {
			line = line.substring(line.indexOf(" ") + 1);
			String[] tokens = line.split(" ");

			try {
				if (tokens.length == 2) {
					return new Vertex(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]));
				} else if (tokens.length == 3) {
					return new Vertex(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
				}
			} catch (NumberFormatException e) {
				throw new Exception(String.format("Number formatting error at line %d", lineCount), e);
			}
		} else {
			throw new Exception("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
		}

		return vertex;
	}

	private Vertex parseVertexNormal(String line, int lineCount) throws Exception {
		Vertex vertexNormal = null;

		if (isValidVertexNormalLine(line)) {
			line = line.substring(line.indexOf(" ") + 1);
			String[] tokens = line.split(" ");

			try {
				if (tokens.length == 3) {
					return new Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
				}
			} catch (NumberFormatException e) {
				throw new Exception(String.format("Number formatting error at line %d", lineCount), e);
			}
		} else {
			throw new Exception("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
		}

		return vertexNormal;
	}

	private TextureCoordinate parseTextureCoordinate(String line, int lineCount) throws Exception {
		TextureCoordinate textureCoordinate = null;

		if (isValidTextureCoordinateLine(line)) {
			line = line.substring(line.indexOf(" ") + 1);
			String[] tokens = line.split(" ");

			try {
				if (tokens.length == 2) {
					return new TextureCoordinate(Float.parseFloat(tokens[0]), 1 - Float.parseFloat(tokens[1]));
				} else if (tokens.length == 3) {
					return new TextureCoordinate(Float.parseFloat(tokens[0]), 1 - Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
				}
			} catch (NumberFormatException e) {
				throw new Exception(String.format("Number formatting error at line %d", lineCount), e);
			}
		} else {
			throw new Exception("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
		}

		return textureCoordinate;
	}

	private ModelFace parseFace(String line, int lineCount) throws Exception {
		ModelFace face = null;

		if (isValidFaceLine(line)) {
			face = new ModelFace();

			String trimmedLine = line.substring(line.indexOf(" ") + 1);
			String[] tokens = trimmedLine.split(" ");
			String[] subTokens = null;

			if (tokens.length == 3) {
				if (currentGroupObject.glDrawingMode == -1) {
					currentGroupObject.glDrawingMode = GL11.GL_TRIANGLES;
				} else if (currentGroupObject.glDrawingMode != GL11.GL_TRIANGLES) {
					throw new Exception("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Invalid number of points for face (expected 4, found " + tokens.length + ")");
				}
			} else if (tokens.length == 4) {
				if (currentGroupObject.glDrawingMode == -1) {
					currentGroupObject.glDrawingMode = GL11.GL_QUADS;
				} else if (currentGroupObject.glDrawingMode != GL11.GL_QUADS) {
					throw new Exception("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Invalid number of points for face (expected 3, found " + tokens.length + ")");
				}
			}

			// f v1/vt1/vn1 v2/vt2/vn2 v3/vt3/vn3 ...
			if (isValidFace_V_VT_VN_Line(line)) {
				face.vertices = new Vertex[tokens.length];
				face.textureCoordinates = new TextureCoordinate[tokens.length];
				face.vertexNormals = new Vertex[tokens.length];

				for (int i = 0; i < tokens.length; ++i) {
					subTokens = tokens[i].split("/");

					face.vertices[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
					face.textureCoordinates[i] = textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
					face.vertexNormals[i] = vertexNormals.get(Integer.parseInt(subTokens[2]) - 1);
				}
			}
			// f v1/vt1 v2/vt2 v3/vt3 ...
			else if (isValidFace_V_VT_Line(line)) {
				face.vertices = new Vertex[tokens.length];
				face.textureCoordinates = new TextureCoordinate[tokens.length];

				for (int i = 0; i < tokens.length; ++i) {
					subTokens = tokens[i].split("/");

					face.vertices[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
					face.textureCoordinates[i] = textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
				}
			}
			// f v1//vn1 v2//vn2 v3//vn3 ...
			else if (isValidFace_V_VN_Line(line)) {
				face.vertices = new Vertex[tokens.length];
				face.vertexNormals = new Vertex[tokens.length];

				for (int i = 0; i < tokens.length; ++i) {
					subTokens = tokens[i].split("//");

					face.vertices[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
					face.vertexNormals[i] = vertexNormals.get(Integer.parseInt(subTokens[1]) - 1);
				}
			}
			// f v1 v2 v3 ...
			else if (isValidFace_V_Line(line)) {
				face.vertices = new Vertex[tokens.length];

				for (int i = 0; i < tokens.length; ++i) {
					face.vertices[i] = vertices.get(Integer.parseInt(tokens[i]) - 1);
				}
			} else {
				throw new Exception("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
			}
			face.faceNormal = face.calculateFaceNormal();
		} else {
			throw new Exception("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
		}

		return face;
	}

	private GroupObject parseGroupObject(String line, int lineCount) throws Exception {
		GroupObject group = null;

		if (isValidGroupObjectLine(line)) {
			String trimmedLine = line.substring(line.indexOf(" ") + 1);

			if (trimmedLine.length() > 0) {
				group = new GroupObject(trimmedLine);
			}
		} else {
			throw new Exception("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
		}

		return group;
	}

	/***
	 * Verifies that the given line from the model file is a valid vertex
	 * 
	 * @param line the line being validated
	 * @return true if the line is a valid vertex, false otherwise
	 */
	private static boolean isValidVertexLine(String line) {
		if (vertexMatcher != null) {
			vertexMatcher.reset();
		}

		vertexMatcher = vertexPattern.matcher(line);
		return vertexMatcher.matches();
	}

	/***
	 * Verifies that the given line from the model file is a valid vertex normal
	 * 
	 * @param line the line being validated
	 * @return true if the line is a valid vertex normal, false otherwise
	 */
	private static boolean isValidVertexNormalLine(String line) {
		if (vertexNormalMatcher != null) {
			vertexNormalMatcher.reset();
		}

		vertexNormalMatcher = vertexNormalPattern.matcher(line);
		return vertexNormalMatcher.matches();
	}

	/***
	 * Verifies that the given line from the model file is a valid texture coordinate
	 * 
	 * @param line the line being validated
	 * @return true if the line is a valid texture coordinate, false otherwise
	 */
	private static boolean isValidTextureCoordinateLine(String line) {
		if (textureCoordinateMatcher != null) {
			textureCoordinateMatcher.reset();
		}

		textureCoordinateMatcher = textureCoordinatePattern.matcher(line);
		return textureCoordinateMatcher.matches();
	}

	/***
	 * Verifies that the given line from the model file is a valid face that is described by vertices, texture coordinates, and vertex normals
	 * 
	 * @param line the line being validated
	 * @return true if the line is a valid face that matches the format "f v1/vt1/vn1 ..." (with a minimum of 3 points in the face, and a maximum of 4), false otherwise
	 */
	private static boolean isValidFace_V_VT_VN_Line(String line) {
		if (face_V_VT_VN_Matcher != null) {
			face_V_VT_VN_Matcher.reset();
		}

		face_V_VT_VN_Matcher = face_V_VT_VN_Pattern.matcher(line);
		return face_V_VT_VN_Matcher.matches();
	}

	/***
	 * Verifies that the given line from the model file is a valid face that is described by vertices and texture coordinates
	 * 
	 * @param line the line being validated
	 * @return true if the line is a valid face that matches the format "f v1/vt1 ..." (with a minimum of 3 points in the face, and a maximum of 4), false otherwise
	 */
	private static boolean isValidFace_V_VT_Line(String line) {
		if (face_V_VT_Matcher != null) {
			face_V_VT_Matcher.reset();
		}

		face_V_VT_Matcher = face_V_VT_Pattern.matcher(line);
		return face_V_VT_Matcher.matches();
	}

	/***
	 * Verifies that the given line from the model file is a valid face that is described by vertices and vertex normals
	 * 
	 * @param line the line being validated
	 * @return true if the line is a valid face that matches the format "f v1//vn1 ..." (with a minimum of 3 points in the face, and a maximum of 4), false otherwise
	 */
	private static boolean isValidFace_V_VN_Line(String line) {
		if (face_V_VN_Matcher != null) {
			face_V_VN_Matcher.reset();
		}

		face_V_VN_Matcher = face_V_VN_Pattern.matcher(line);
		return face_V_VN_Matcher.matches();
	}

	/***
	 * Verifies that the given line from the model file is a valid face that is described by only vertices
	 * 
	 * @param line the line being validated
	 * @return true if the line is a valid face that matches the format "f v1 ..." (with a minimum of 3 points in the face, and a maximum of 4), false otherwise
	 */
	private static boolean isValidFace_V_Line(String line) {
		if (face_V_Matcher != null) {
			face_V_Matcher.reset();
		}

		face_V_Matcher = face_V_Pattern.matcher(line);
		return face_V_Matcher.matches();
	}

	/***
	 * Verifies that the given line from the model file is a valid face of any of the possible face formats
	 * 
	 * @param line the line being validated
	 * @return true if the line is a valid face that matches any of the valid face formats, false otherwise
	 */
	private static boolean isValidFaceLine(String line) {
		return isValidFace_V_VT_VN_Line(line) || isValidFace_V_VT_Line(line) || isValidFace_V_VN_Line(line) || isValidFace_V_Line(line);
	}

	/***
	 * Verifies that the given line from the model file is a valid group (or object)
	 * 
	 * @param line the line being validated
	 * @return true if the line is a valid group (or object), false otherwise
	 */
	private static boolean isValidGroupObjectLine(String line) {
		if (groupObjectMatcher != null) {
			groupObjectMatcher.reset();
		}

		groupObjectMatcher = groupObjectPattern.matcher(line);
		return groupObjectMatcher.matches();
	}
}