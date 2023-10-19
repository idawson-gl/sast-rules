// License: LGPL-3.0 License (c) security-code-scan

using System.Text.Json;

class InsecureDeserialization {
    private void ConvertData(string json)
    {
        JsonSerializer.Deserialize<string>(json);
    }
}
