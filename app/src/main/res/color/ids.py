import os
import re
import collections
import argparse # For command-line arguments

# --- Configuration ---
DEFAULT_PROJECT_ROOT = 'C:/Users/ayach/Desktop/MyISAMM' # <--- CHANGE THIS if not using arguments
OUTPUT_FILENAME = 'android_all_xml_ids.txt' # Renamed output file
OUTPUT_DUPLICATES_FILENAME = 'android_duplicate_xml_ids.txt' # Renamed output file
# -------------------

def find_all_xml_files(project_root):
    """Finds ALL XML files within the entire project directory."""
    print(f"Searching for ALL XML files in: {project_root}")
    if not os.path.isdir(project_root):
        print(f"Error: Project root directory does not exist: {project_root}")
        return None

    xml_files = []
    # Walk through the entire project directory tree
    for root, _, files in os.walk(project_root):
        # --- Optional: Add exclusion rules if needed ---
        # Example: Exclude build directories
        if os.path.sep + 'build' + os.path.sep in root:
             continue
        # Example: Exclude .git directory
        if os.path.sep + '.git' + os.path.sep in root:
            continue
        # Example: Exclude .gradle directory
        if os.path.sep + '.gradle' + os.path.sep in root:
            continue
        # ---------------------------------------------

        for file in files:
            if file.lower().endswith('.xml'):
                xml_files.append(os.path.join(root, file))
    return xml_files

def extract_ids_from_file(filepath):
    """Extracts all @+id/... definitions from a single XML file."""
    # Regex to find @+id/ followed by valid ID characters
    id_pattern = re.compile(r'@\+id/([a-zA-Z0-9_]+)')
    ids_found = []
    try:
        with open(filepath, 'r', encoding='utf-8', errors='ignore') as f: # Added errors='ignore' for potential non-UTF8 files
            for line_num, line in enumerate(f, 1):
                matches = id_pattern.finditer(line)
                for match in matches:
                    id_name = match.group(1) # Get the captured group (the ID name)
                    ids_found.append({'id': id_name, 'file': filepath, 'line': line_num})
    except Exception as e:
        print(f"Warning: Could not read file {filepath}: {e}")
    return ids_found

def main(project_root):
    """Main function to find files, extract IDs, and report."""
    # Use the updated function to find ALL XML files
    all_xml_files = find_all_xml_files(project_root)
    if not all_xml_files:
        print("No XML files found in the project.")
        return

    print(f"Found {len(all_xml_files)} XML files. Processing...")

    all_ids_data = []
    for filepath in all_xml_files:
        all_ids_data.extend(extract_ids_from_file(filepath))

    if not all_ids_data:
        print("No '@+id/...' definitions found in any XML files.")
        return

    print(f"Found a total of {len(all_ids_data)} ID definitions.")

    # --- Write all found IDs to a file ---
    output_path = os.path.join(os.getcwd(), OUTPUT_FILENAME)
    try:
        with open(output_path, 'w', encoding='utf-8') as f_out:
            f_out.write("ID_Name, Filepath, Line_Number\n") # Header
            for data in sorted(all_ids_data, key=lambda x: (x['id'], x['file'], x['line'])):
                relative_path = os.path.relpath(data['file'], project_root)
                f_out.write(f"{data['id']},{relative_path},{data['line']}\n")
        print(f"Successfully wrote all found IDs to: {output_path}")
    except Exception as e:
        print(f"Error writing output file {output_path}: {e}")


    # --- Identify and report duplicates ---
    id_locations = collections.defaultdict(list)
    for data in all_ids_data:
        id_locations[data['id']].append(data)

    duplicates = {id_name: locations for id_name, locations in id_locations.items() if len(locations) > 1}

    if not duplicates:
        print("No duplicate IDs found. Good job!")
    else:
        print(f"\nWARNING: Found {len(duplicates)} duplicate ID(s)!")
        duplicates_output_path = os.path.join(os.getcwd(), OUTPUT_DUPLICATES_FILENAME)
        try:
            with open(duplicates_output_path, 'w', encoding='utf-8') as f_dup:
                f_dup.write("--- Duplicate Android '@+id/...' Definitions Across All XML Files ---\n\n")
                for id_name, locations in sorted(duplicates.items()):
                    f_dup.write(f"Duplicate ID: \"{id_name}\" found in:\n")
                    print(f"  Duplicate ID: \"{id_name}\" found in:")
                    for loc in sorted(locations, key=lambda x: (x['file'], x['line'])):
                        relative_path = os.path.relpath(loc['file'], project_root)
                        location_str = f"  - File: {relative_path}, Line: {loc['line']}\n"
                        f_dup.write(location_str)
                        print(location_str, end='') # Also print to console
                    f_dup.write("\n") # Add separator
            print(f"\nDetailed duplicate report written to: {duplicates_output_path}")
        except Exception as e:
            print(f"Error writing duplicates file {duplicates_output_path}: {e}")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Extract @+id definitions from ALL Android XML files and find duplicates.")
    parser.add_argument("project_root", nargs='?', default=DEFAULT_PROJECT_ROOT,
                        help="Path to the root directory of your Android project. Defaults to the path set in the script.")
    args = parser.parse_args()

    if args.project_root == '/path/to/your/Android/project' and not os.path.isdir(args.project_root):
         print("Error: Please provide the path to your Android project root as a command-line argument,")
         print("       or update the DEFAULT_PROJECT_ROOT variable inside the script.")
    else:
        main(args.project_root)