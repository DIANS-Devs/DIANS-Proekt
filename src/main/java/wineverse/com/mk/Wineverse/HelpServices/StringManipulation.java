package mk.com.wineverse.HelpServices;

public class StringManipulation {
    public String[] parse_JSON(String entryString) {
        String cleanString = entryString.replace("[", "").replace("]", "");
        if (cleanString.length() > 2) {
            cleanString = cleanString.substring(1, cleanString.length() - 1);
        }
        String[] values = cleanString.split(",");
        return edit_JSON(values);
    }
    public String[] edit_JSON(String [] entry_array){
        for(int i = 0; i < entry_array.length;i++){
            if(i == 0)
                entry_array[i] = entry_array[i].substring(0, entry_array[i].length()-1);
            else if(i == entry_array.length-1)
                entry_array[i] = entry_array[i].substring(1);
            else
                entry_array[i] = entry_array[i].substring(1, entry_array[i].length()-1);
        }
        return entry_array;
    }
}
